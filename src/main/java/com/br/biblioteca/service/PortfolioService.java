package com.br.biblioteca.service;

import com.br.biblioteca.dto.PortfolioCreateDTO;
import com.br.biblioteca.dto.PortfolioFilterDTO;
import com.br.biblioteca.dto.PortfolioUpdateDTO;
import com.br.biblioteca.dto.projection.PortfolioSummaryDTO;
import com.br.biblioteca.entity.BookEntity;
import com.br.biblioteca.entity.PortfolioEntity;
import com.br.biblioteca.repository.BookRepository;
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

    public Page<PortfolioSummaryDTO> pesquisarPaginado(PortfolioFilterDTO dto, Pageable pageable) {
        List<PortfolioSummaryDTO> modelos = repository.findByResume(dto, pageable);
        return new PageImpl<>(modelos, pageable, modelos.size());
    }

    public PortfolioEntity cadastrar(PortfolioCreateDTO dto) {
        BookEntity book = bookRepository.findById(dto.getBookIsbn())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setId(generateId());
        portfolio.setBook(book);
        portfolio.setCondition(dto.getCondition());
        portfolio.setCover(dto.getCover());

        return repository.save(portfolio);
    }

    public void deletar(String id) {
        PortfolioEntity portfolio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item do portfólio não encontrado"));

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
        // Gera um ID de 26 caracteres (ULID-like ou UUID sem hífens + padding)
        return UUID.randomUUID().toString().replace("-", "").substring(0, 26);
    }

}
