package com.epam.esm.util.enums;

import com.epam.esm.util.FieldName;

public enum OrderField implements FieldName {
    ID;

    @Override
    public String getName() {
        return "order " + this.name().toLowerCase();
    }
}
