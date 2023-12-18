package com.epam.esm.entity.filter.sort;

public enum SortOrder {
    ASCENDING("asc"),
    DESCENDING("desc");

    private final String orderValue;

    SortOrder(String orderValue) {
        this.orderValue = orderValue;
    }

    public String getOrderValue() {
        return orderValue;
    }
}

