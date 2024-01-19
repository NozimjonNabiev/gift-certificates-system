package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.Set;

/**
 * Utility class for generating test data instances of entities and DTOs.
 *
 */
@UtilityClass
public class TestDataFactory {
    private final Tag TAG;
    private final TagDTO TAG_DTO;
    private final Tag NULL_TAG;
    private final TagDTO NULL_TAG_DTO;
    private final User USER;
    private final UserDTO USER_DTO;
    private final User NULL_USER;
    private final UserDTO NULL_USER_DTO;
    private final Order ORDER;
    private final OrderDTO ORDER_DTO;
    private final Order NULL_ORDER;
    private final OrderDTO NULL_ORDER_DTO;
    private final GiftCertificate GIFT_CERTIFICATE;
    private final GiftCertificateDTO GIFT_CERTIFICATE_DTO;
    private final GiftCertificate NULL_GIFT_CERTIFICATE;
    private final GiftCertificateDTO NULL_CERTIFICATE_DTO;
    private final Pagination PAGINATION = new Pagination(0, 0);

    static {
        TAG = Tag.builder().id(0L).name("").build();
        TAG_DTO = TagDTO.builder().id(0L).name("").build();

        NULL_TAG = Tag.builder().build();
        NULL_TAG_DTO = TagDTO.builder().build();

        USER = User.builder().id(0L).username("").password("")
                .firstName("").lastName("").email("").orders(Collections.emptyList()).build();
        USER_DTO = UserDTO.builder().id(0L).username("").password("")
                .firstName("").lastName("").email("").build();

        NULL_USER = User.builder().orders(Collections.emptyList()).build();
        NULL_USER_DTO = UserDTO.builder().build();

        GIFT_CERTIFICATE = GiftCertificate.builder().id(0L).name("").description("")
                .price(0.0).duration(0).tags(Set.of()).orders(Collections.emptyList()).build();
        GIFT_CERTIFICATE_DTO = GiftCertificateDTO.builder().id(0L).name("").description("")
                .price(0.0).duration(0).tags(Set.of()).build();

        NULL_GIFT_CERTIFICATE = GiftCertificate.builder()
                .tags(Set.of()).orders(Collections.emptyList()).build();
        NULL_CERTIFICATE_DTO = GiftCertificateDTO.builder()
                .tags(Set.of()).build();

        ORDER = Order.builder().id(0L).price(GIFT_CERTIFICATE.getPrice())
                .user(USER).giftCertificate(GIFT_CERTIFICATE).build();
        ORDER_DTO = OrderDTO.builder().id(0L).price(GIFT_CERTIFICATE.getPrice())
                .userId(USER.getId()).giftCertificateId(GIFT_CERTIFICATE.getId()).build();

        NULL_ORDER = Order.builder()
                .user(USER).giftCertificate(GIFT_CERTIFICATE).build();
        NULL_ORDER_DTO = OrderDTO.builder()
                .userId(USER.getId()).giftCertificateId(GIFT_CERTIFICATE.getId()).build();
    }

    // entity

    public GiftCertificate getGiftCertificate() {
        return GIFT_CERTIFICATE;
    }

    public Order getOrder() {
        return ORDER;
    }

    public Tag getTag() {
        return TAG;
    }

    public User getUser() {
        return USER;
    }

    // entity dto

    public GiftCertificateDTO getGiftCertificateDTO() {
        return GIFT_CERTIFICATE_DTO;
    }

    public OrderDTO getOrderDTO() {
        return ORDER_DTO;
    }

    public TagDTO getTagDTO() {
        return TAG_DTO;
    }

    public UserDTO getUserDTO() {
        return USER_DTO;
    }

    // null entity

    public GiftCertificate getNullGiftCertificate() {
        return NULL_GIFT_CERTIFICATE;
    }

    public Order getNullOrder() {
        return NULL_ORDER;
    }

    public Tag getNullTag() {
        return NULL_TAG;
    }

    public User getNullUser() {
        return NULL_USER;
    }

    // null entity dto

    public GiftCertificateDTO getNullGiftCertificateDTO() {
        return NULL_CERTIFICATE_DTO;
    }

    public OrderDTO getNullOrderDTO() {
        return NULL_ORDER_DTO;
    }

    public TagDTO getNullTagDTO() {
        return NULL_TAG_DTO;
    }

    public UserDTO getNullUserDTO() {
        return NULL_USER_DTO;
    }

    public Pagination getPagination() {
        return PAGINATION;
    }

    public Pagination getPagination(int page, int size) {
        return new Pagination(page, size);
    }
}
