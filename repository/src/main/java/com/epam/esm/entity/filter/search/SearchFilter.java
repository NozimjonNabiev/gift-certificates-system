package com.epam.esm.entity.filter.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilter {
    private String searchValue;
    private SearchPlace searchPlace;
    private SearchType searchType;
}
