package com.br.biblioteca.enums;

public enum CoverEnum {
    PAPER("paper"),
    HARDCOVER("hardcover");

    private final String value;

    CoverEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CoverEnum fromValue(String value) {
        for (CoverEnum cover : CoverEnum.values()) {
            if (cover.value.equals(value)) {
                return cover;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Cover: " + value);
    }

}
