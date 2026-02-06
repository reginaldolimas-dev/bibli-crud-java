package com.br.biblioteca.controller;

import com.br.biblioteca.dto.PortfolioCreateDTO;
import com.br.biblioteca.dto.PortfolioFilterDTO;
import com.br.biblioteca.dto.PortfolioUpdateDTO;
import com.br.biblioteca.dto.projection.PortfolioSummaryDTO;
import com.br.biblioteca.entity.PortfolioEntity;
import com.br.biblioteca.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/portfolios")
@AllArgsConstructor
public class PortfolioController {

    private final PortfolioService service;

    @GetMapping
    public ResponseEntity<Page<PortfolioSummaryDTO>> pesquisarPaginado(PortfolioFilterDTO dto, Pageable pageable) {
        return ResponseEntity.ok(service.pesquisarPaginado(dto, pageable));
    }

    @PostMapping
    public ResponseEntity<PortfolioEntity> cadastrar(@RequestBody @Valid PortfolioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody PortfolioUpdateDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable String id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

}
