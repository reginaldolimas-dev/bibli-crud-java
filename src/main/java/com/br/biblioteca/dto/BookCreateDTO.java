package com.br.biblioteca.dto;

import lombok.Value;

@Value
public class BookCreateDTO {
    private String isbn;
    private String title;
    private Integer releaseYear;
    private String summary;
    private String author;
    private Integer pageLen;
    private String publisher;
}
