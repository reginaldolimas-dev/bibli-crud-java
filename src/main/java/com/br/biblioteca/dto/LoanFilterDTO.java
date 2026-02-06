package com.br.biblioteca.dto;

import lombok.Data;

@Data
public class LoanFilterDTO {

    private String userId;
    private String portfolioId;
    private String bookId;
}
