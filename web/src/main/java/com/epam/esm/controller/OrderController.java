package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.enums.field.GiftCertificateField;
import com.epam.esm.util.enums.field.OrderField;
import com.epam.esm.util.enums.field.UserField;
import com.epam.esm.validator.CustomValidator;
import com.epam.esm.validator.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final OrderService orderService;
    private final HateoasAdder<OrderDTO> orderHateoasAdder;

    /**
     * Retrieves all orders with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link OrderDTO}.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<OrderDTO> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PaginationValidator.validate(page, size);

        List<OrderDTO> orders = orderService.findAllByPage(page, size);
        orderHateoasAdder.addLinksToEntityList(orders);
        return orders;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order.
     * @return The {@link OrderDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderDTO getById(@PathVariable long id) {
        CustomValidator.validateId(OrderField.ID, id);

        OrderDTO order = orderService.findById(id);
        orderHateoasAdder.addLinksToEntity(order);
        return order;
    }

    /**
     * Creates a new order.
     *
     * @param orderDTO The {@link OrderDTO} representing the new order.
     * @return The created {@link OrderDTO}.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        CustomValidator.validateId(UserField.ID, orderDTO.getUserId());
        CustomValidator.validateId(GiftCertificateField.ID, orderDTO.getGiftCertificateId());

        OrderDTO order = orderService.create(orderDTO);
        orderHateoasAdder.addLinksToEntity(order);
        return order;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<OrderDTO> getByGiftCertificateOrUserId(
            @RequestParam(required = false) Long giftCertificateId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        if (giftCertificateId == null && userId == null || giftCertificateId != null && userId != null) {
            throw new ValidationException("either giftCertificateId or userId should be passed, not both or neither");
        }

        PaginationValidator.validate(page, size);

        List<OrderDTO> orders;
        if (giftCertificateId != null) {
            CustomValidator.validateId(GiftCertificateField.ID, giftCertificateId);
            orders = orderService.findByGiftCertificateIdAndPage(giftCertificateId, page, size);
        } else {
            CustomValidator.validateId(UserField.ID, userId);
            orders = orderService.findByUserIdAndPage(userId, page, size);
        }

        orderHateoasAdder.addLinksToEntityList(orders);
        return orders;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<OrderDTO> getOrdersByUserIdAndPage(@PathVariable Long id,
                                                   @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                   @RequestParam(required = false, defaultValue = "5", name = "size") int size) {
        PaginationValidator.validate(page, size);
        CustomValidator.validateId(UserField.ID, id);
        List<OrderDTO> orders = orderService.findByUserIdAndPage(id, page, size);
        orderHateoasAdder.addLinksToEntityList(orders);
        return orders;
    }
}
