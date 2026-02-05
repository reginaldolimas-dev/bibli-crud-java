package com.br.biblioteca.dto;

import com.br.biblioteca.enums.ConditionEnum;
import com.br.biblioteca.enums.CoverEnum;
import lombok.Value;

@Value
public class PortfolioFilterDTO {
    private String id;
    private String bookIsbn;
}
