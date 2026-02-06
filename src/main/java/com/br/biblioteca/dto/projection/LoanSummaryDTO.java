package com.br.biblioteca.dto.projection;

import java.time.LocalDateTime;

public interface LoanSummaryDTO {
    String getId();
    String getPortfolioId();
    String getBookTitle();
    String getBookAuthor();
    String getUserId();
    String getUserName();
    LocalDateTime getStartAt();
    LocalDateTime getReturnAt();
    Integer getPeriod();
}
