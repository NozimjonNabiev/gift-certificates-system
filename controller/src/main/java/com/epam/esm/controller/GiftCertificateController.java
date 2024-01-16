package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.MessageHolder;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.util.SearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code GiftCertificateController} is a Spring MVC RestController class that handles
 * HTTP requests related to gift certificates. It provides endpoints for retrieving,
 * searching, creating, updating, and deleting gift certificates.
 *
 * <p>The class is annotated with {@link RestController}, {@link RequiredArgsConstructor},
 * and {@link RequestMapping}, indicating that it is a controller component with
 * constructor-based dependency injection and a base request mapping of "/api/gift-certificates".
 *
 * <p>Endpoints include:
 * <ul>
 *     <li>{@code GET /api/gift-certificates}: Retrieves all gift certificates with pagination.</li>
 *     <li>{@code GET /api/gift-certificates/{id}}: Retrieves a gift certificate by its ID.</li>
 *     <li>{@code POST /api/gift-certificates/search}: Searches for gift certificates based on filters.</li>
 *     <li>{@code POST /api/gift-certificates}: Creates a new gift certificate.</li>
 *     <li>{@code PATCH /api/gift-certificates/{id}/price}: Updates the price of a gift certificate by its ID.</li>
 *     <li>{@code PATCH /api/gift-certificates/{id}}: Updates a gift certificate by its ID.</li>
 *     <li>{@code DELETE /api/gift-certificates/{id}}: Deletes a gift certificate by its ID.</li>
 * </ul>
 *
 * <p>Response types include {@link GiftCertificateDTO} for successful operations and
 * {@link MessageHolder} for error responses.
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateFacade giftCertificateFacade;

    /**
     * Retrieves all gift certificates with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link GiftCertificateDTO}.
     */
    @GetMapping
    public List<GiftCertificateDTO> getAllByPage(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        return giftCertificateFacade.findAllByPage(page, size);
    }

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate.
     * @return The {@link GiftCertificateDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    public GiftCertificateDTO getById(@PathVariable Long id) {
        return giftCertificateFacade.findById(id);
    }

    /**
     * Searches for gift certificates based on filters.
     *
     * @param page         The page number for pagination.
     * @param size         The number of items per page.
     * @param searchFilter The {@link SearchFilter} object containing search criteria.
     * @return A list of {@link GiftCertificateDTO} matching the search criteria.
     */
    @PostMapping("/search")
    public List<GiftCertificateDTO> search(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                           @RequestBody(required = false) SearchFilter searchFilter) {
        return giftCertificateFacade.findByFilter(searchFilter, page, size);
    }

    /**
     * Creates a new gift certificate.
     *
     * @param giftCertificateDTO The {@link GiftCertificateDTO} representing the new gift certificate.
     * @return The created {@link GiftCertificateDTO}.
     */
    @PostMapping
    public GiftCertificateDTO create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateFacade.create(giftCertificateDTO);
    }

    /**
     * Updates the price of a gift certificate by its ID.
     *
     * @param id                  The ID of the gift certificate.
     * @param giftCertificateDTO The {@link GiftCertificateDTO} containing the updated price.
     * @return The updated {@link GiftCertificateDTO}.
     */
    @PatchMapping("/{id}/price")
    public GiftCertificateDTO updatePriceById(@PathVariable Long id,
                                              @RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateFacade.updatePriceById(id, giftCertificateDTO);
    }

    /**
     * Updates a gift certificate by its ID.
     *
     * @param id                  The ID of the gift certificate.
     * @param giftCertificateDTO The {@link GiftCertificateDTO} containing the updates.
     * @return The updated {@link GiftCertificateDTO}.
     */
    @PatchMapping("/{id}")
    public GiftCertificateDTO update(@PathVariable Long id,
                                     @RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateFacade.update(id, giftCertificateDTO);
    }

    /**
     * Deletes a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate to delete.
     * @return A {@link MessageHolder} indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    public MessageHolder deleteById(@PathVariable Long id) {
        return giftCertificateFacade.deleteById(id);
    }
}
