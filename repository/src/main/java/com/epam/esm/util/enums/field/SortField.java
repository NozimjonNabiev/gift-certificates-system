package com.epam.esm.util.enums.field;

import com.epam.esm.util.FieldName;

public enum SortField implements FieldName {
    TYPE,
    ORDER;

    @Override
    public String getName() {
        return "sort " + this.name().toLowerCase();
    }
}
