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
    public void atualizar(String id, LoanUpdateDTO dto) {
        LoanEntity loan = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if (loan.getReturnAt() != null) {
            throw new IllegalStateException("Não é possível atualizar um empréstimo já devolvido");
        }

        if (dto.getPeriod() != null) {
            if (dto.getPeriod() < 1 || dto.getPeriod() > 365) {
                throw new IllegalArgumentException("O período deve estar entre 1 e 365 dias");
            }
            loan.setPeriod(dto.getPeriod());
        }

        repository.save(loan);
    }

    @Transactional
    public LoanEntity devolver(String id, LoanReturnDTO dto) {
        LoanEntity loan = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if (loan.getReturnAt() != null) {
            throw new IllegalStateException("Este empréstimo já foi devolvido");
        }

        loan.setReturnAt(LocalDateTime.now());
        loan.setReturnCondition(dto.getReturnCondition());

        // Atualiza a condição do item no portfólio se houver degradação
        if (dto.getReturnCondition() != null) {
            loan.getPortfolio().setCondition(dto.getReturnCondition());
            portfolioRepository.save(loan.getPortfolio());
        }

        return repository.save(loan);
    }

    @Transactional
    public LoanEntity renovar(String id, Integer novosPeriodoDias) {
        LoanEntity loan = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if (loan.getReturnAt() != null) {
            throw new IllegalStateException("Não é possível renovar um empréstimo já devolvido");
        }

        if (novosPeriodoDias == null || novosPeriodoDias < 1 || novosPeriodoDias > 90) {
            throw new IllegalArgumentException("O período de renovação deve estar entre 1 e 90 dias");
        }

        loan.setPeriod(loan.getPeriod() + novosPeriodoDias);

        return repository.save(loan);
    }
}
