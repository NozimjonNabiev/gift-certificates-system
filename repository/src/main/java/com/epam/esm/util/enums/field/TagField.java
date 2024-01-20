package com.epam.esm.util.enums.field;

import com.epam.esm.util.FieldName;

public enum TagField implements FieldName {
    ID,
    NAME;

    @Override
    public String getName() {
        return "tag " + this.name().toLowerCase();
    }
}
