package com.br.biblioteca.enums;

public enum GenreEnum {
    ADVENTURE("adventure"),
    ROMANCE("romance"),
    FANTASY("fantasy"),
    SCI_FI("sci_fi"),
    HISTORY("history"),
    HORROR("horror"),
    DISTOPIAN("distopian"),
    BIOGRAPHY("biography"),
    SELF_HELP("self_help"),
    MEMORY("memory"),
    TRUE_CRIME("true_crime"),
    POETRY("poetry"),
    GRAPHIC_NOVEL("graphic_novel");

    private final String value;

    GenreEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GenreEnum fromValue(String value) {
        for (GenreEnum genre : GenreEnum.values()) {
            if (genre.value.equals(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Genre: " + value);
    }
}
