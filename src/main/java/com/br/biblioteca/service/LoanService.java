package com.br.biblioteca.service;

import com.br.biblioteca.dto.*;
import com.br.biblioteca.dto.projection.LoanSummaryDTO;
import com.br.biblioteca.entity.LoanEntity;
import com.br.biblioteca.entity.PortfolioEntity;
import com.br.biblioteca.entity.UserEntity;
import com.br.biblioteca.repository.LoanRepository;
import com.br.biblioteca.repository.PortfolioRepository;
import com.br.biblioteca.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository repository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public Page<LoanSummaryDTO> pesquisarPaginado(LoanFilterDTO dto, Pageable pageable) {
        List<LoanSummaryDTO> modelos = repository.findByResume(dto, pageable);
        return new PageImpl<>(modelos, pageable, modelos.size());
    }

    @Transactional
    public LoanResponseDTO cadastrar(LoanCreateDTO dto) {
        PortfolioEntity portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new EntityNotFoundException("Item do portfólio não encontrado"));

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!user.getActive()) {
            throw new IllegalStateException("Usuário inativo não pode realizar empréstimos");
        }

        repository.findActiveByPortfolioId(dto.getPortfolioId())
                .ifPresent(loan -> {
                    throw new IllegalStateException("Este item já está emprestado");
                });

        LoanEntity loan = new LoanEntity();
        loan.setPortfolio(portfolio);
        loan.setUser(user);
        loan.setStartAt(LocalDateTime.now());
        loan.setPeriod(dto.getPeriod() != null ? dto.getPeriod() : 30);
        loan.setLoanCondition(dto.getLoanCondition() != null ? dto.getLoanCondition() : portfolio.getCondition());

        loan = repository.save(loan);

        return repository.findProjectionById(loan.getId())
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));
    }

    @Transactional
    public void deletar(String id) {
        LoanEntity loan = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if (loan.getReturnAt() != null) {
            throw new IllegalStateException("Não é possível deletar um empréstimo já devolvido");
        }

        repository.delete(loan);
    }

    @Transactional
    public LoanResponseDTO devolver(String id, LoanReturnDTO dto) {
        LoanEntity loan = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if (loan.getReturnAt() != null) {
            throw new IllegalStateException("Este empréstimo já foi devolvido");
        }

        loan.setReturnAt(LocalDateTime.now());
        loan.setReturnCondition(dto.getReturnCondition());

        if (dto.getReturnCondition() != null) {
            loan.getPortfolio().setCondition(dto.getReturnCondition());
            portfolioRepository.save(loan.getPortfolio());
        }

        LoanEntity loanSaved = repository.save(loan);

        return repository.findProjectionById(loanSaved.getId())
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));
    }
}
