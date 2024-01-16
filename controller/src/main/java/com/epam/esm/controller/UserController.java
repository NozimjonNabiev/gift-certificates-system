package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code UserController} is a Spring MVC RestController class that handles
 * HTTP requests related to users. It provides endpoints for retrieving users,
 * finding a user's most used tag, and retrieving a user by ID.
 *
 * <p>The class is annotated with {@link RestController}, {@link RequiredArgsConstructor},
 * and {@link RequestMapping}, indicating that it is a controller component with
 * constructor-based dependency injection and a base request mapping of "/api/users".
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>{@code GET /api/users}: Retrieves all users with pagination.</li>
 *     <li>{@code GET /api/users/{id}}: Retrieves a user by its ID.</li>
 *     <li>{@code GET /api/users/{id}/most-used}: Retrieves the most used tag of a user
 *     with the highest order cost.</li>
 * </ul>
 *
 * <p>Response types include {@link UserDTO} for user-related operations and
 * {@link TagDTO} for the most used tag operation.
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserFacade userFacade;

    /**
     * Retrieves all users with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link UserDTO}.
     */
    @GetMapping
    public List<UserDTO> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userFacade.findAllByPage(page, size);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user.
     * @return The {@link UserDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable long id) {
        return userFacade.findById(id);
    }

    /**
     * Retrieves the most used tag of a user with the highest order cost.
     *
     * @param id The ID of the user.
     * @return The {@link TagDTO} representing the most used tag.
     */
    @GetMapping("/{id}/most-used")
    public TagDTO getMostUsedTag(@PathVariable Long id) {
        return userFacade.findMostUsedTagOfUserWithHighestOrderCost(id);
    }
}
