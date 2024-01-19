package com.epam.esm.util.enums.field;

import com.epam.esm.util.FieldName;

public enum PaginationField implements FieldName {
    NUMBER,
    SIZE;

    @Override
    public String getName() {
        return "page " + this.name().toLowerCase();
    }
}
