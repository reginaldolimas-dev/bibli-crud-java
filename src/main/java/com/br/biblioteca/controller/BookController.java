package com.br.biblioteca.controller;

import com.br.biblioteca.dto.BookCreateDTO;
import com.br.biblioteca.dto.BookFilterDTO;
import com.br.biblioteca.dto.BookUpdateDTO;
import com.br.biblioteca.dto.projection.BookSummaryDTO;
import com.br.biblioteca.entity.BookEntity;
import com.br.biblioteca.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping
    public ResponseEntity<Page<BookSummaryDTO>> pesquisarPaginado(BookFilterDTO dto, Pageable pageable) {
        return ResponseEntity.ok(service.pesquisarPaginado(dto, pageable));
    }

    @PostMapping
    public ResponseEntity<BookEntity> cadastrar(@RequestBody @Valid BookCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrar(dto));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Void> atualizar(@PathVariable String isbn, @RequestBody BookUpdateDTO dto) {
        service.atualizar(isbn, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deletar(@PathVariable String isbn) {
        service.deletar(isbn);
        return ResponseEntity.noContent().build();
    }

}
