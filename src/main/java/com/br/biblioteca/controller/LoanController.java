package com.br.biblioteca.controller;

import com.br.biblioteca.dto.*;
import com.br.biblioteca.dto.projection.LoanSummaryDTO;
import com.br.biblioteca.entity.LoanEntity;
import com.br.biblioteca.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {

    private final LoanService service;

    @GetMapping
    public ResponseEntity<Page<LoanSummaryDTO>> pesquisarPaginado(LoanFilterDTO dto, Pageable pageable) {
        return ResponseEntity.ok(service.pesquisarPaginado(dto, pageable));
    }

    @PostMapping
    public ResponseEntity<LoanResponseDTO> cadastrar(@RequestBody LoanCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody LoanUpdateDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<LoanEntity> devolver(
            @PathVariable String id,
            @RequestBody LoanReturnDTO dto) {
        return ResponseEntity.ok(service.devolver(id, dto));
    }

    @PostMapping("/{id}/renovar")
    public ResponseEntity<LoanEntity> renovar(
            @PathVariable String id,
            @RequestParam Integer periodoEmDias) {
        return ResponseEntity.ok(service.renovar(id, periodoEmDias));
    }

}
