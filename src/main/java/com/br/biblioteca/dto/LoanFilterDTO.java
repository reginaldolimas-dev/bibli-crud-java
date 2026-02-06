package com.br.biblioteca.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LoanFilterDTO {
    String id;
    String portfolioId;
    String userId;
    String userName;
    String bookTitle;
    LocalDateTime startAtInicio;
    LocalDateTime startAtFim;
    Boolean devolvido; // null = todos, true = devolvidos, false = não devolvidos
    Boolean atrasado; // true = empréstimos em atraso
}
