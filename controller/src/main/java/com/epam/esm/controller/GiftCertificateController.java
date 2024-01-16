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
     * Retrieves all gift certificates.
     *
     * @return ResponseData containing a list of GiftCertificateDTO.
     * @throws NotFoundException if no certificates are found.
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
    
    /*
     * Retrieves gift certificates by tag name.
     *
     * @param name The name of the tag.
     * @return ResponseData containing a list of GiftCertificateDTO.
     * @throws NotFoundException if no certificates are found for the given tag.
     */
    @GetMapping(value = "/tag")
    public ResponseData<List<GiftCertificateDTO>> getByTag(@RequestParam("name") String name)
            throws NotFoundException {
        log.info("Processing get request for certificates by tags...");
        TagDTO tagDTO = TagDTO.builder()
                .name(name)
                .build();
        return new ResponseData<>(giftCertificateService.findByTag(tagDTO));
    }

    /**
     * Retrieves gift certificates by search filter criteria.
     *
     * @param searchValue The value to search for.
     * @param searchType  The type of search.
     * @param searchPlace The place to search for the value.
     * @return ResponseData containing a list of GiftCertificateDTO.
     * @throws NotFoundException if no certificates are found for the given search criteria.
     */
    @GetMapping(value = "/search")
    public ResponseData<List<GiftCertificateDTO>> getBySearchFilter(
            @RequestParam("searchValue") String searchValue,
            @RequestParam("searchType") String searchType,
            @RequestParam("searchPlace") String searchPlace)
            throws NotFoundException {
        log.info("Processing get request for certificates by search filter...");
        SearchFilterDTO searchFilter = SearchFilterDTO.builder()
                .searchPlace(searchPlace)
                .searchValue(searchValue)
                .searchType(searchType)
                .build();
        return new ResponseData<>(giftCertificateService.findBySearchFilter(searchFilter));
    }

    /**
     * Retrieves gift certificates by sort filter criteria.
     *
     * @param sortType  The type of sorting.
     * @param sortOrder The order of sorting.
     * @return ResponseData containing a list of GiftCertificateDTO.
     * @throws NotFoundException if no certificates are found for the given sort criteria.
     */
    @GetMapping(value = "/sort")
    public ResponseData<List<GiftCertificateDTO>> getBySortFilter(
            @RequestParam("sortType") String sortType,
            @RequestParam("sortOrder") String sortOrder
    )
            throws NotFoundException {
        log.info("Processing post request for certificates by sort filter...");
        SortFilterDTO sortFilter = SortFilterDTO.builder()
                .sortType(sortType)
                .sortOrder(sortOrder)
                .build();
        return new ResponseData<>(giftCertificateService.findBySortFilter(sortFilter));
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
     * @param giftCertificate The GiftCertificateDTO to be created.
     * @param bindingResult   The binding result.
     * @return ResponseData indicating the success of the operation.
     * @throws InvalidRequestBodyException if the request body is invalid.
     * @throws DataModificationException   if an error occurs during certificate creation.
     */
    @PostMapping
    public ResponseData<Object> create(@RequestBody @Valid GiftCertificateDTO giftCertificate,
                                       BindingResult bindingResult)
            throws InvalidRequestBodyException, DataModificationException {
        log.info("Processing post request for creating a new certificate...");
        RequestBodyValidator.validate(bindingResult);
        giftCertificateService.create(giftCertificate);
        log.info("Certificate was successfully created...");
        return new ResponseData<>(HttpStatus.CREATED, "Certificate was successfully created!");
    }

    /**
     * Updates an existing gift certificate.
     *
     * @param giftCertificate The updated GiftCertificateDTO.
     * @param bindingResult   The binding result.
     * @return ResponseData indicating the success of the operation.
     * @throws InvalidRequestBodyException if the request body is invalid.
     * @throws NotFoundException          if the gift certificate to be updated is not found.
     * @throws DataModificationException   if an error occurs during gift certificate update.
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
     /*
     * @param id The ID of the gift certificate to be deleted.
     * @return ResponseData indicating the success of the deletion.
     * @throws NotFoundException       if the gift certificate to be deleted is not found.
     * @throws DataModificationException if an error occurs during gift certificate deletion.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData<Object> deleteById(@PathVariable("id") Long id)
            throws NotFoundException, DataModificationException {
        log.info("Processing delete request for gift certificate by id...");
        giftCertificateService.delete(id);
        log.info("Gift certificate has been successfully deleted...");
        return new ResponseData<>(HttpStatus.NO_CONTENT, "Gift certificate has been successfully deleted!");
    }
}
