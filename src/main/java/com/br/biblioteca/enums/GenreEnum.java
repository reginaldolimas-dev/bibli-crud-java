package com.br.biblioteca.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenreEnum {
    ADVENTURE,
    ROMANCE,
    FANTASY,
    SCI_FI,
    HISTORY,
    HORROR,
    DISTOPIAN,
    BIOGRAPHY,
    SELF_HELP,
    MEMORY,
    TRUE_CRIME,
    POETRY,
    GRAPHIC_NOVEL;

    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static GenreEnum fromValue(String value) {
        return GenreEnum.valueOf(value.toUpperCase());
    }
}
