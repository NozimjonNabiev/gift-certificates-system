package com.epam.esm.controller;

import com.epam.esm.dto.TokenDTO;
import com.epam.esm.hateoas.impl.TokenHateoasAdder;
import com.epam.esm.service.TokenService;
import com.epam.esm.util.enums.field.TagField;
import com.epam.esm.validator.CustomValidator;
import com.epam.esm.validator.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@code TokenController} is a Spring MVC RestController class that handles
 * HTTP requests related to tokens. It provides endpoints for retrieving tokens
 * with pagination and retrieving a token by ID.
 *
 * <p>The class is annotated with {@link RestController}, {@link RequiredArgsConstructor},
 * and {@link RequestMapping}, indicating that it is a controller component with
 * constructor-based dependency injection and a base request mapping of "/api/tokens".
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>{@code GET /api/tokens}: Retrieves all tokens with pagination.</li>
 *     <li>{@code GET /api/tokens/{id}}: Retrieves a token by its ID.</li>
 * </ul>
 *
 * <p>Response types include {@link TokenDTO} for token-related operations.
 *
 * @see RestController
 * @see RequiredArgsConstructor
 * @see RequestMapping
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class TokenController {
    private final TokenService tokenService;
    private final TokenHateoasAdder tokenHateoasAdder;

    /**
     * Retrieves all tokens with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link TokenDTO}.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<TokenDTO> getAllByPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size) {
        PaginationValidator.validate(page, size);

        List<TokenDTO> tokens = tokenService.findAllByPage(page, size);
        tokenHateoasAdder.addLinksToEntityList(tokens);
        return tokens;
    }

    /**
     * Retrieves a token by its ID.
     *
     * @param id The ID of the token.
     * @return The {@link TokenDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TokenDTO getById(@PathVariable Long id) {
        CustomValidator.validateId(TagField.ID, id);

        TokenDTO token = tokenService.findById(id);
        tokenHateoasAdder.addLinksToEntity(token);
        return token;
    }
}
