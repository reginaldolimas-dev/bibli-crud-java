package com.br.biblioteca.dto;

import com.br.biblioteca.enums.ConditionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class LoanCreateDTO {
    @NotBlank(message = "ID do portfólio é obrigatório")
    String portfolioId;

    @NotBlank(message = "ID do usuário é obrigatório")
    String userId;

    @NotNull(message = "Período do empréstimo é obrigatório")
    @Positive(message = "Período deve ser maior que zero")
    Integer period;

    @NotNull(message = "Condição do empréstimo é obrigatória")
    ConditionEnum loanCondition;
}
