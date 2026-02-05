package com.br.biblioteca.dto;

import lombok.Value;

@Value
public class BookFilterDTO {
    private String isbn;
    private String title;
    private String author;
    // falta busca por genero
}
