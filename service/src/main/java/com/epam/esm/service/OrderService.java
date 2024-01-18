package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;

import java.util.List;

/**
 * Service interface for managing {@link OrderDTO} entities.
 *
 */
public interface OrderService {

    /**
     * Retrieves a list of orders based on pagination parameters.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return A list of orders matching the pagination criteria.
     */
    List<OrderDTO> findAllByPage(int page, int size);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return The order with the given ID.
     */
    OrderDTO findById(Long id);

    /**
     * Retrieves a list of orders based on a user's unique identifier and pagination parameters.
     *
     * @param userId The unique identifier of the user.
     * @param page   The page number.
     * @param size   The size of each page.
     * @return A list of orders matching the user's identifier and pagination criteria.
     */
    List<OrderDTO> findByUserIdAndPage(Long userId, int page, int size);

    /**
     * Retrieves a list of orders based on a gift certificate's unique identifier and pagination parameters.
     *
     * @param giftCertificateId The unique identifier of the gift certificate.
     * @param page              The page number.
     * @param size              The size of each page.
     * @return A list of orders matching the gift certificate's identifier and pagination criteria.
     */
    List<OrderDTO> findByGiftCertificateIdAndPage(Long giftCertificateId, int page, int size);

    /**
     * Creates a new order.
     *
     * @param orderDTO The order data to create.
     * @return The created order.
     */
    OrderDTO create(OrderDTO orderDTO);
}
