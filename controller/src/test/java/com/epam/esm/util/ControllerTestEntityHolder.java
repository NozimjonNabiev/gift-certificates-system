package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class ControllerTestEntityHolder {
    public static final TagDTO tag;
    public static final GiftCertificateDTO certificate;
    public static final SortFilterDTO invalidSortFilter;
    public static final SearchFilterDTO invalidSearchFilter;

    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2020, 2, 20, 10, 10, 10);

    static {
        tag = TagDTO
                .builder()
                .id(0L)
                .name("name")
                .build();

        certificate = GiftCertificateDTO
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price(0.0)
                .duration(0)
                .createDate(String.valueOf(localDateTime))
                .lastUpdateDate(String.valueOf(localDateTime))
                .tags(List.of(new TagDTO[]{tag}))
                .build();

        invalidSortFilter = SortFilterDTO
                .builder()
                .sortType("type")
                .sortOrder("order")
                .build();

        invalidSearchFilter = SearchFilterDTO
                .builder()
                .searchValue("value")
                .searchType("type")
                .searchPlace("place")
                .build();
    }
}
