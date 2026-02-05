package com.br.biblioteca.dto.projection;

public interface BookSummaryDTO {
    String getIsbn();
    String getTitle();
    String getAuthor();
    Integer getReleaseYear();
}
