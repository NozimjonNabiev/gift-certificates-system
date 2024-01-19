package com.epam.esm.util.enums.field;

public enum TokenField {
    ID,
    ACCESS_TOKEN,
    TYPE,
    CREATED_DATE,
    EXPIRED,
    USER;

    public String getName() {
        return "token " + name().toLowerCase();
    }
}
