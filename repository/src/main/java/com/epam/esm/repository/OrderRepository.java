package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.util.Pagination;

import java.util.List;

/**
 * Repository interface for managing {@link Order} entities.
 *
 */
public interface OrderRepository extends BaseRepository<Order> {

    /**
     * Retrieves a list of orders based on pagination parameters.
     *
     * @param pagination The pagination information to specify the subset of orders to retrieve.
     * @return A list of orders matching the criteria defined by the provided {@link Pagination} object.
     */
    List<Order> findAllByPage(Pagination pagination);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return The order with the given ID.
     */
    Order findById(Long id);

    /**
     * Retrieves a list of orders based on a gift certificate's unique identifier and pagination parameters.
     *
     * @param giftCertificateId The unique identifier of the gift certificate.
     * @param pagination        The pagination information to specify the subset of orders to retrieve.
     * @return A list of orders matching the gift certificate's identifier and pagination criteria.
     */
    List<Order> findByGiftCertificateId(Long giftCertificateId, Pagination pagination);

    /**
     * Retrieves a list of orders based on a user's unique identifier and pagination parameters.
     *
     * @param userId     The unique identifier of the user.
     * @param pagination The pagination information to specify the subset of orders to retrieve.
     * @return A list of orders matching the user's identifier and pagination criteria.
     */
    List<Order> findByUserId(Long userId, Pagination pagination);

    /**
     * Saves a new order.
     *
     * @param order The order to be saved.
     */
    void save(Order order);
}
