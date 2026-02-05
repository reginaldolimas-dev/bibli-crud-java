package com.br.biblioteca.service;

import com.br.biblioteca.dto.BookCreateDTO;
import com.br.biblioteca.dto.BookFilterDTO;
import com.br.biblioteca.dto.BookUpdateDTO;
import com.br.biblioteca.dto.projection.BookSummaryDTO;
import com.br.biblioteca.entity.BookEntity;
import com.br.biblioteca.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Page<BookSummaryDTO> pesquisarPaginado(BookFilterDTO dto, Pageable pageable) {
        List<BookSummaryDTO> modelos = repository.findByResume(dto, pageable);
        return new PageImpl<>(modelos, pageable, modelos.size());
    }

    public BookEntity cadastrar(BookCreateDTO dto) {
        boolean isbnAlreadyExists = repository.existsByIsbn(dto.getIsbn());

        if (isbnAlreadyExists) {
            throw new IllegalArgumentException("ISBN já cadastrado");
        }

        BookEntity book = new BookEntity();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setReleaseYear(dto.getReleaseYear());
        book.setSummary(dto.getSummary());
        book.setAuthor(dto.getAuthor());
        book.setPageLen(dto.getPageLen());
        book.setPublisher(dto.getPublisher());

        return repository.save(book);
    }

    public void deletar(String isbn) {
        BookEntity book = repository.findById(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        book.setActive(false);
        book.setInactivedAt(LocalDateTime.now());

        repository.save(book);
    }

    public void atualizar(String isbn, BookUpdateDTO dto) {
        BookEntity book = repository.findById(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        if (dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }

        if (dto.getSummary() != null) {
            book.setSummary(dto.getSummary());
        }

        if (dto.getAuthor() != null) {
            book.setAuthor(dto.getAuthor());
        }

        if (dto.getPageLen() != null) {
            book.setPageLen(dto.getPageLen());
        }

        if (dto.getPublisher() != null) {
            book.setPublisher(dto.getPublisher());
        }

        repository.save(book);
    }
}
