package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code OrderController} is a Spring MVC RestController class that handles
 * HTTP requests related to orders. It provides endpoints for retrieving, creating,
 * and searching orders.
 *
 * <p>The class is annotated with {@link RestController}, {@link RequiredArgsConstructor},
 * and {@link RequestMapping}, indicating that it is a controller component with
 * constructor-based dependency injection and a base request mapping of "/api/orders".
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>{@code GET /api/orders}: Retrieves all orders with pagination.</li>
 *     <li>{@code GET /api/orders/{id}}: Retrieves an order by its ID.</li>
 *     <li>{@code POST /api/orders}: Creates a new order.</li>
 *     <li>{@code GET /api/orders/search}: Searches for orders based on gift certificate ID,
 *     user ID, page, and size.</li>
 *     <li>{@code GET /api/orders/user/{id}}: Retrieves orders for a specific user with pagination.</li>
 * </ul>
 *
 * <p>Response types include {@link OrderDTO} for successful operations.
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    /**
     * Retrieves all orders with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link OrderDTO}.
     */
    @GetMapping
    public List<OrderDTO> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return orderFacade.findAllByPage(page, size);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order.
     * @return The {@link OrderDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable long id) {
        return orderFacade.findById(id);
    }

    /**
     * Creates a new order.
     *
     * @param orderDTO The {@link OrderDTO} representing the new order.
     * @return The created {@link OrderDTO}.
     */
    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        return orderFacade.create(orderDTO);
    }

    /**
     * Searches for orders based on gift certificate ID, user ID, page, and size.
     *
     * @param giftCertificateId The ID of the gift certificate.
     * @param userId            The ID of the user.
     * @param page              The page number for pagination.
     * @param size              The number of items per page.
     * @return A list of {@link OrderDTO} matching the search criteria.
     */
    @GetMapping("/search")
    public List<OrderDTO> getByGiftCertificateOrUserId(
            @RequestParam(required = false) Long giftCertificateId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return orderFacade.findByGiftCertificateOrUserId(giftCertificateId, userId, page, size);
    }

    /**
     * Retrieves orders for a specific user with pagination.
     *
     * @param id   The ID of the user.
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link OrderDTO} for the specified user ID.
     */
    @GetMapping("/user/{id}")
    public List<OrderDTO> getOrdersByUserIdAndPage(@PathVariable Long id,
                                                   @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                   @RequestParam(required = false, defaultValue = "5", name = "size") int size) {
        return orderFacade.findOrdersByUserIdAndPage(id, page, size);
    }
}
