package com.epam.esm.entity.filter.search;

public enum SearchType {
    DESC("description"),
    TITLE("name");

    private final String fieldName;

    SearchType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
