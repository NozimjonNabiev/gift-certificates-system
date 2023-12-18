package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Utility class holding static entities for repository test cases
 */
@UtilityClass
public class RepositoryTestEntityHolder {

    public static final Tag tag;
    public static final GiftCertificate giftCertificate;

    // Common LocalDateTime instance used in test entities
    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2020, 2, 20, 10, 10, 10);

    // Initialize static entities
    static {
        // Sample Tag instance
        tag = Tag.builder()
                .id(13L)
                .name("Name")
                .build();

        // Sample Certificate instance
        giftCertificate = GiftCertificate.builder()
                .id(0L)
                .name("name")
                .description("description")
                .price(0.0)
                .duration(0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .tags(Set.of(tag))
                .build();
    }
}
