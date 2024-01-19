package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.TokenDTO;
import com.epam.esm.dto.UserCredentialsDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.hateoas.impl.TokenHateoasAdder;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.enums.field.UserField;
import com.epam.esm.util.mapper.UserMapper;
import com.epam.esm.validator.CustomValidator;
import com.epam.esm.validator.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code UserController} is a Spring MVC RestController class that handles
 * HTTP requests related to users. It provides endpoints for retrieving users,
 * finding a user's most used tag, and performing user registration and authentication.
 *
 * <p>The class is annotated with {@link RestController}, {@link RequiredArgsConstructor},
 * and {@link RequestMapping}, indicating that it is a controller component with
 * constructor-based dependency injection and a base request mapping of "/api/users".
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>{@code GET /api/users}: Retrieves all users with pagination.</li>
 *     <li>{@code GET /api/users/{id}}: Retrieves a user by its ID.</li>
 *     <li>{@code GET /api/users/{id}/most-used-tag}: Retrieves the most used tag of a user
 *     with the highest order cost.</li>
 *     <li>{@code POST /api/users/register}: Registers a new user.</li>
 *     <li>{@code POST /api/users/authenticate}: Authenticates a user.</li>
 * </ul>
 *
 * <p>Response types include {@link UserDTO} for user-related operations,
 * {@link TagDTO} for the most used tag operation, and {@link TokenDTO} for authentication.
 *
 * <p>The class utilizes HATEOAS (Hypermedia As The Engine Of Application State) for
 * adding hypermedia links to the responses.
 *
 * @see RestController
 * @see RequiredArgsConstructor
 * @see RequestMapping
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final TagService tagService;
    private final UserMapper userMapper;
    private final TokenHateoasAdder tokenHateoasAdder;
    private final HateoasAdder<UserDTO> userHateoasAdder;
    private final HateoasAdder<TagDTO> tagHateoasAdder;

    /**
     * Retrieves all users with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link UserDTO}.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UserDTO> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PaginationValidator.validate(page, size);

        List<UserDTO> users = userService.findAllByPage(page, size);
        userHateoasAdder.addLinksToEntityList(users);
        return users;
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user.
     * @return The {@link UserDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserDTO getById(@PathVariable long id) {
        CustomValidator.validateId(UserField.ID, id);

        UserDTO user = userService.findById(id);
        userHateoasAdder.addLinksToEntity(user);
        return user;
    }

    /**
     * Retrieves the most used tag of a user with the highest order cost.
     *
     * @param id The ID of the user.
     * @return The {@link TagDTO} representing the most used tag.
     */
    @GetMapping("/{id}/most-used-tag")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<TagDTO> getMostUsedTag(@PathVariable Long id) {
        List<TagDTO> tag = tagService.findMostUsedTagOfUserWithHighestOrderCost(id);
        tagHateoasAdder.addLinksToEntityList(tag);
        return tag;
    }

    /**
     * Registers a new user.
     *
     * @param userCredentialsDTO The user credentials for registration.
     * @return The {@link TokenDTO} representing the authentication token.
     */
    @PostMapping("/register")
    public TokenDTO signUp(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        TokenDTO token = userService.signUp(userMapper.toUser(userCredentialsDTO));
        tokenHateoasAdder.addLinksToEntity(token);
        return token;
    }

    /**
     * Authenticates a user.
     *
     * @param userCredentialsDTO The user credentials for authentication.
     * @return The {@link TokenDTO} representing the authentication token.
     */
    @PostMapping("/authenticate")
    public TokenDTO signIn(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        TokenDTO token = userService.signIn(userMapper.toUser(userCredentialsDTO));

        tokenHateoasAdder.addLinksToEntity(token);
        return token;
    }
}
