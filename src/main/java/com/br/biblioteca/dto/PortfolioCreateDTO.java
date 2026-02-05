package com.br.biblioteca.dto;

import com.br.biblioteca.enums.ConditionEnum;
import com.br.biblioteca.enums.CoverEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PortfolioCreateDTO {
    @NotBlank(message = "ISBN do livro é obrigatório")
    String bookIsbn;

    @NotNull(message = "Condição do livro é obrigatória")
    ConditionEnum condition;

    @NotNull(message = "Tipo de capa é obrigatório")
    CoverEnum cover;
}
