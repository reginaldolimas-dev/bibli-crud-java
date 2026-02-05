package com.br.biblioteca.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ConditionEnum {
    PERFECT,
    GOOD,
    BAD,
    USELESS,
    DISABLE;

    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static ConditionEnum fromValue(String value) {
        return ConditionEnum.valueOf(value.toUpperCase());
    }
}
