package com.br.biblioteca.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LoanFilterDTO {

    String userId;
    String portfolioId;
    String bookId;
}
