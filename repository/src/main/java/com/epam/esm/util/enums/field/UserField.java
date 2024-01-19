package com.epam.esm.util.enums.field;

import com.epam.esm.util.FieldName;

public enum UserField implements FieldName {
    ID,
    ROLE,
    USERNAME,
    PASSWORD,
    FIRSTNAME,
    LASTNAME,
    EMAIL_ADDRESS,
    BIRTH_DATE;

    @Override
    public String getName() {
        return "user " + this.name().toLowerCase();
    }
}
