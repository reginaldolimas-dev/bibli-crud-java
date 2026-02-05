package com.br.biblioteca.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CoverEnum {
    PAPER,
    HARDCOVER;


    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static CoverEnum fromValue(String value) {
        return CoverEnum.valueOf(value.toUpperCase());
    }

}
