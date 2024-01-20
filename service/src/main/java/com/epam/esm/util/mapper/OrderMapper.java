package com.epam.esm.util.mapper;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting {@link Order} entities to {@link OrderDTO} DTOs.
 *
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Converts an {@link Order} entity to an {@link OrderDTO} DTO.
     *
     * @param order The source Order entity.
     * @return The mapped OrderDTO.
     */
    @Mapping(target = "userId", expression = "java(order.getUser().getId())")
    @Mapping(target = "giftCertificateId", expression = "java(order.getGiftCertificate().getId())")
    OrderDTO toOrderDTO(Order order);
}
