package com.epam.esm.entity.filter.search;

public enum SearchPlace {
    STARTS_WITH("' %'"),
    CONTAINS("'% %'"),
    ENDS_WITH("'% '");

    private final String pattern;

    SearchPlace(String pattern) {
        this.pattern = pattern;
    }

    public String getFormattedValue(String searchValue) {
        return pattern.replace(" ", searchValue);
    }
}

