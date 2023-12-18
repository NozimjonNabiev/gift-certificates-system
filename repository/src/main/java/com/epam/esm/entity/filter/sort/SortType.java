package com.epam.esm.entity.filter.sort;

public enum SortType {
    BY_NAME("name"),
    BY_CREATE_DATE("create_date"),
    BY_LAST_UPDATE_DATE("last_update_date");

    private final String fieldType;

    SortType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return fieldType;
    }
}

