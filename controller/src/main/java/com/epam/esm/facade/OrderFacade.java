package com.epam.esm.facade;

import com.epam.esm.dto.OrderDTO;

import java.util.List;

/**
 * The {@code OrderFacade} interface extends the {@link BaseFacade} interface
 * and provides additional operations specific to orders. It includes methods
 * for finding orders by gift certificate ID or user ID, and finding orders by
 * user ID with pagination.
 *
 */
public interface OrderFacade extends BaseFacade<OrderDTO> {

    /**
     * Retrieves all orders with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link OrderDTO}.
     */
    @Override
    List<OrderDTO> findAllByPage(int page, int size);

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order.
     * @return The {@link OrderDTO} for the specified ID.
     */
    @Override
    OrderDTO findById(Long id);

    /**
     * Finds orders based on the provided gift certificate ID, user ID, and pagination.
     *
     * @param giftCertificateId The ID of the gift certificate.
     * @param userId            The ID of the user.
     * @param page              The page number for pagination.
     * @param size              The number of items per page.
     * @return A list of {@link OrderDTO} matching the search criteria.
     */
    List<OrderDTO> findByGiftCertificateOrUserId(Long giftCertificateId, Long userId, int page, int size);

    /**
     * Finds orders for a specific user with pagination.
     *
     * @param userId The ID of the user.
     * @param page   The page number for pagination.
     * @param size   The number of items per page.
     * @return A list of {@link OrderDTO} for the specified user ID.
     */
    List<OrderDTO> findOrdersByUserIdAndPage(Long userId, int page, int size);
}
