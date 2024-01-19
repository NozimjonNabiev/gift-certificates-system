package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.MessageHolder;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.SearchFilter;
import com.epam.esm.util.enums.field.GiftCertificateField;
import com.epam.esm.validator.CustomValidator;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.SortValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GiftCertificateService giftCertificateService;
    private final HateoasAdder<GiftCertificateDTO> giftCertificateHateoasAdder;
    private final HateoasAdder<MessageHolder> messageHolderHateoasAdder;

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
        PaginationValidator.validate(page, size);

        List<GiftCertificateDTO> giftCertificates = giftCertificateService.findAllByPage(page, size);
        giftCertificateHateoasAdder.addLinksToEntityList(giftCertificates);
        return giftCertificates;
    }

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate.
     * @return The {@link GiftCertificateDTO} for the specified ID.
     */
    @GetMapping("/{id}")
    public GiftCertificateDTO getById(@PathVariable Long id) {
        CustomValidator.validateId(GiftCertificateField.ID, id);

        GiftCertificateDTO giftCertificate = giftCertificateService.findById(id);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<GiftCertificateDTO> search(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                           @RequestBody(required = false) SearchFilter searchFilter) {
        PaginationValidator.validate(page, size);
        SortValidator.validate(searchFilter.sortType(), searchFilter.sortOrder());

        List<GiftCertificateDTO> giftCertificates = giftCertificateService.findByFilterAndPage(searchFilter, page, size);
        giftCertificateHateoasAdder.addLinksToEntityList(giftCertificates);
        return giftCertificates;
    }

    /**
     * Creates a new gift certificate.
     *
     * @param giftCertificateDTO The {@link GiftCertificateDTO} representing the new gift certificate.
     * @return The created {@link GiftCertificateDTO}.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateDTO create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateValidator.validate(giftCertificateDTO);

        GiftCertificateDTO giftCertificate = giftCertificateService.create(giftCertificateDTO);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * Updates the price of a gift certificate by its ID.
     *
     * @param id                  The ID of the gift certificate.
     * @param giftCertificateDTO The {@link GiftCertificateDTO} containing the updated price.
     * @return The updated {@link GiftCertificateDTO}.
     */
    @PatchMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateDTO updatePriceById(@PathVariable Long id,
                                              @RequestBody GiftCertificateDTO giftCertificateDTO) {
        CustomValidator.validateId(GiftCertificateField.ID, id);
        GiftCertificateValidator.validatePrice(giftCertificateDTO.getPrice());

        GiftCertificateDTO giftCertificate = giftCertificateService.updatePriceById(id, giftCertificateDTO);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * Updates a gift certificate by its ID.
     *
     * @param id                  The ID of the gift certificate.
     * @param giftCertificateDTO The {@link GiftCertificateDTO} containing the updates.
     * @return The updated {@link GiftCertificateDTO}.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateDTO update(@PathVariable Long id,
                                     @RequestBody GiftCertificateDTO giftCertificateDTO) {
        CustomValidator.validateId(GiftCertificateField.ID, id);
        GiftCertificateValidator.validate(giftCertificateDTO);
        GiftCertificateDTO giftCertificate = giftCertificateService.update(id, giftCertificateDTO);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificate);
        return giftCertificate;
    }

    /**
     * Deletes a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate to delete.
     * @return A {@link MessageHolder} indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageHolder deleteById(@PathVariable Long id) {
        CustomValidator.validateId(GiftCertificateField.ID, id);

        giftCertificateService.deleteById(id);
        MessageHolder messageHolder = new MessageHolder(
                HttpStatus.OK, "gift certificate has been successfully deleted");
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return messageHolder;
    }
}
