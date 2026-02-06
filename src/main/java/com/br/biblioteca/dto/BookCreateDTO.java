package com.br.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class BookCreateDTO {
    @NotBlank(message = "ISBN é obrigatório")
    String isbn;

    @NotBlank(message = "Título é obrigatório")
    String title;

    @NotNull(message = "Ano de lançamento é obrigatório")
    @Positive(message = "Ano de lançamento deve ser maior que zero")
    Integer releaseYear;

    @NotBlank(message = "Resumo é obrigatório")
    String summary;

    @NotBlank(message = "Autor é obrigatório")
    String author;

    @NotNull(message = "Número de páginas é obrigatório")
    @Positive(message = "Número de páginas deve ser maior que zero")
    Integer pageLen;

    @NotBlank(message = "Editora é obrigatória")
    String publisher;
}
