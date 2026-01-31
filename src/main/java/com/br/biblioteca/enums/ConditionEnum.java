package com.br.biblioteca.enums;

public enum ConditionEnum {
    PERFECT("perfect"),
    GOOD("good"),
    BAD("bad"),
    USELESS("useless"),
    DISABLE("disable");

    private final String value;

    ConditionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConditionEnum fromValue(String value) {
        for (ConditionEnum condition : ConditionEnum.values()) {
            if (condition.value.equals(value)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Condition: " + value);
    }
}
