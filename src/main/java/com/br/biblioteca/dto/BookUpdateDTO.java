package com.br.biblioteca.dto;

import lombok.Value;

@Value
public class BookUpdateDTO {
    private String title;
    private String summary;
    private String author;
    private Integer pageLen;
    private String publisher;
}
