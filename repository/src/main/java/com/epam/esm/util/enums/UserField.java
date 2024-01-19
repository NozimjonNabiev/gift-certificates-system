package com.epam.esm.util.enums;

import com.epam.esm.util.FieldName;

public enum UserField implements FieldName {
    ID;

    @Override
    public String getName() {
        return "user " + this.name().toLowerCase();
    }
}
