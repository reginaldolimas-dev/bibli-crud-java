package com.br.biblioteca.service;

import com.br.biblioteca.dto.LoanFilterDTO;
import com.br.biblioteca.dto.PortfolioCreateDTO;
import com.br.biblioteca.dto.PortfolioFilterDTO;
import com.br.biblioteca.dto.PortfolioUpdateDTO;
import com.br.biblioteca.dto.projection.LoanSummaryDTO;
import com.br.biblioteca.dto.projection.PortfolioSummaryDTO;
import com.br.biblioteca.entity.BookEntity;
import com.br.biblioteca.entity.LoanEntity;
import com.br.biblioteca.entity.PortfolioEntity;
import com.br.biblioteca.repository.BookRepository;
import com.br.biblioteca.repository.LoanRepository;
import com.br.biblioteca.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PortfolioService {


    private final PortfolioRepository repository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public Page<PortfolioSummaryDTO> pesquisarPaginado(PortfolioFilterDTO dto, Pageable pageable) {
        List<PortfolioSummaryDTO> modelos = repository.findByResume(dto, pageable);
        return new PageImpl<>(modelos, pageable, modelos.size());
    }

    public PortfolioEntity cadastrar(PortfolioCreateDTO dto) {
        BookEntity book = bookRepository.findById(dto.getBookIsbn())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        if (!Boolean.TRUE.equals(book.getActive())) {
            throw new IllegalArgumentException("Não pode cadastrar um portfolio para um livro inativo.");
        }

        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setId(generateId());
        portfolio.setBook(book);
        portfolio.setCondition(dto.getCondition());
        portfolio.setCover(dto.getCover());

        return repository.save(portfolio);
    }

    public void inativar(String id) {
        PortfolioEntity portfolio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item do portfólio não encontrado"));

        boolean existsActiveLoan = loanRepository.existsActiveLoanByPortfolioId(portfolio.getId());

        if  (existsActiveLoan) {
            throw new IllegalArgumentException("Operação não permitida: o portfólio possui empréstimo ativo.");
        }

        portfolio.setActive(false);
        portfolio.setInactivedAt(LocalDateTime.now());
        repository.save(portfolio);
    }

    public void atualizar(String id, PortfolioUpdateDTO dto) {
        PortfolioEntity portfolio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item do portfólio não encontrado"));

        if (dto.getCondition() != null) {
            portfolio.setCondition(dto.getCondition());
        }

        if (dto.getCover() != null) {
            portfolio.setCover(dto.getCover());
        }

        repository.save(portfolio);
    }

    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 26);
    }

}
