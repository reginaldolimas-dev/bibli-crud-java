package com.br.biblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class BookEntity {
    @Id
    @Column(name = "isbn", length = 13, nullable = false)
    private String isbn;

    @Column(name = "title", length = 512, nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "author", nullable = false, columnDefinition = "TEXT")
    private String author;

    @Column(name = "page_len")
    private Integer pageLen;

    @Column(name = "publisher", columnDefinition = "TEXT")
    private String publisher;
}
