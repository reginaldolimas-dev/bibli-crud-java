package com.br.biblioteca.dto;

import com.br.biblioteca.enums.ConditionEnum;

import java.time.LocalDateTime;

public interface LoanResponseDTO {
    String getId();

    LocalDateTime getStartAt();

    Integer getPeriod();

    ConditionEnum getLoanCondition();

    String getPortfolioId();

    String getBookTitle();

    String getUserId();

    String getUserName();
}
