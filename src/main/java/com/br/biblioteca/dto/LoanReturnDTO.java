package com.br.biblioteca.dto;

import com.br.biblioteca.enums.ConditionEnum;
import lombok.Value;

@Value
public class LoanReturnDTO {
    ConditionEnum returnCondition;
}
