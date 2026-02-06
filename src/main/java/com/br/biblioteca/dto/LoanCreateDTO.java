package com.br.biblioteca.dto;

import com.br.biblioteca.enums.ConditionEnum;
import lombok.Value;

@Value
public class LoanCreateDTO {
    String portfolioId;
    String userId;
    Integer period; // Período em dias (padrão 30)
    ConditionEnum loanCondition;
}
