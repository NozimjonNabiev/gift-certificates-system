package com.epam.esm.util.enums;

import com.epam.esm.util.FieldName;

public enum GiftCertificateField implements FieldName {
    ID,
    NAME,
    DESCRIPTION,
    PRICE,
    DURATION,
    TAGS;

    @Override
    public String getName() {
        return "gift certificate " + this.name().toLowerCase();
    }
}
