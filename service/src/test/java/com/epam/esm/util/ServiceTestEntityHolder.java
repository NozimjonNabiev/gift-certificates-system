package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.search.SearchPlace;
import com.epam.esm.entity.filter.search.SearchType;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.entity.filter.sort.SortOrder;
import com.epam.esm.entity.filter.sort.SortType;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Utility class holding static entities for tests
 */
@UtilityClass
public class ServiceTestEntityHolder {

    // Entity
    public static final Tag tag;
    public static final GiftCertificate giftCertificate;
    public static final SortFilter sortFilter;
    public static final SearchFilter searchFilter;

    // Entity DTO
    public static final TagDTO tagDTO;
    public static final GiftCertificateDTO giftCertificateDTO;
    public static final SortFilterDTO sortFilterDTO;
    public static final SearchFilterDTO searchFilterDTO;

    // Null Entities and DTOs
    public static final Tag nullTag;
    public static final GiftCertificate nullGiftCertificate;
    public static final SortFilter nullSortFilter;
    public static final SearchFilter nullSearchFilter;
    public static final TagDTO nullTagDTO;
    public static final GiftCertificateDTO nullGiftCertificateDTO;
    public static final SortFilterDTO nullSortFilterDTO;
    public static final SearchFilterDTO nullSearchFilterDTO;

    private static final LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 20, 10, 10, 10);

    static {
        tag = Tag
                .builder()
                .id(0L)
                .name("name")
                .build();

        giftCertificate = GiftCertificate
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price(0.0)
                .duration(0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .tags(Set.of(tag))
                .build();

        sortFilter = SortFilter
                .builder()
                .sortType(SortType.BY_NAME)
                .sortOrder(SortOrder.ASCENDING)
                .build();

        searchFilter = SearchFilter
                .builder()
                .searchValue("name")
                .searchType(SearchType.TITLE)
                .searchPlace(SearchPlace.STARTS_WITH)
                .build();

        tagDTO = TagDTO
                .builder()
                .id(0L)
                .name("name")
                .build();

        giftCertificateDTO = GiftCertificateDTO
                .builder()
                .id(0L)
                .name("name")
                .description("description")
                .price(0.0)
                .duration(0)
                .createDate(localDateTime.toString())
                .lastUpdateDate(localDateTime.toString())
                .tags(List.of(new TagDTO[]{tagDTO}))
                .build();

        sortFilterDTO = SortFilterDTO
                .builder()
                .sortType(SortType.BY_NAME.toString())
                .sortOrder(SortOrder.ASCENDING.toString())
                .build();

        searchFilterDTO = SearchFilterDTO
                .builder()
                .searchValue("name")
                .searchType(SearchType.TITLE.toString())
                .searchPlace(SearchPlace.STARTS_WITH.toString())
                .build();

        nullTag = Tag
                .builder()
                .build();

        nullGiftCertificate = GiftCertificate
                .builder()
                .build();

        nullSortFilter = SortFilter
                .builder()
                .build();

        nullSearchFilter = SearchFilter
                .builder()
                .build();

        nullTagDTO = TagDTO
                .builder()
                .build();

        nullGiftCertificateDTO = GiftCertificateDTO
                .builder()
                .build();

        nullSortFilterDTO = SortFilterDTO
                .builder()
                .build();

        nullSearchFilterDTO = SearchFilterDTO
                .builder()
                .build();
    }
}
