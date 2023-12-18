package com.epam.esm.util.mapper;

import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchPlace;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.entity.filter.sort.SortOrder;
import com.epam.esm.entity.filter.sort.SortType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper to convert filter DTOs into filter entities
 */
@Mapper(componentModel = "spring")
public interface FilterMapper {
    @Mapping(target = "sortType", expression = "java(toSortType(sortFilterDTO.getSortType()))")
    @Mapping(target = "sortOrder", expression = "java(toSortOrder(sortFilterDTO.getSortOrder()))")
    SortFilter toSortFilter(SortFilterDTO sortFilterDTO);

    @Mapping(target = "searchType", expression = "java(toSearchType(searchFilterDTO.getSearchType()))")
    @Mapping(target = "searchPlace", expression = "java(toSearchPlace(searchFilterDTO.getSearchPlace()))")
    SearchFilter toSearchFilter(SearchFilterDTO searchFilterDTO);

    default SortType toSortType(String sortType) {
        return sortType == null ? null : SortType.valueOf(sortType.toUpperCase());
    }

    default SortOrder toSortOrder(String sortOrder) {
        return sortOrder == null ? null : SortOrder.valueOf(sortOrder.toUpperCase());
    }

    default SearchType toSearchType(String searchType) {
        return searchType == null ? null : SearchType.valueOf(searchType.toUpperCase());
    }

    default SearchPlace toSearchPlace(String searchPlace) {
        return searchPlace == null ? null : SearchPlace.valueOf(searchPlace.toUpperCase());
    }
}
