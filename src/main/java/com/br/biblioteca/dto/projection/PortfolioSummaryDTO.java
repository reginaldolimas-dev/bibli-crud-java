package com.br.biblioteca.dto.projection;

import com.br.biblioteca.enums.ConditionEnum;
import com.br.biblioteca.enums.CoverEnum;

public interface PortfolioSummaryDTO {
    String getId();
    String getBookIsbn();
    String getBookTitle();
    String getBookAuthor();
    ConditionEnum getCondition();
    CoverEnum getCover();
}
