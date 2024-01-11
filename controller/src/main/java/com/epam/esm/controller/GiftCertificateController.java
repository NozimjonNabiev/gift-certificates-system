package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.exception.InvalidRequestBodyException;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.response.ResponseData;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.RequestBodyValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling operations related to gift certificates.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Retrieves all gift certificates.
     *
     * @return ResponseData containing a list of GiftCertificateDTO.
     * @throws NotFoundException if no certificates are found.
     */
    @GetMapping
    public ResponseData<List<GiftCertificateDTO>> getAll() throws NotFoundException {
        log.info("Processing get request for all certificates...");
        return new ResponseData<>(giftCertificateService.findAll());
    }

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate.
     * @return ResponseData containing a GiftCertificateDTO.
     * @throws NotFoundException if the gift certificate with the given ID is not found.
     */
    @GetMapping(value = "/{id}")
    public ResponseData<GiftCertificateDTO> getById(@PathVariable("id") Long id) throws NotFoundException {
        log.info("Processing get request for gift certificate by id...");
        return new ResponseData<>(giftCertificateService.findById(id));
    }

    /**
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
    @PatchMapping
    public ResponseData<Object> edit(@RequestBody @Valid GiftCertificateDTO giftCertificate,
                                     BindingResult bindingResult)
            throws InvalidRequestBodyException, NotFoundException, DataModificationException {
        log.info("Processing patch request for editing gift certificate...");
        RequestBodyValidator.validate(bindingResult);
        giftCertificateService.update(giftCertificate);
        log.info("Certificate was successfully updated...");
        return new ResponseData<>(HttpStatus.OK, "Certificate was successfully updated!");
    }

    /**
     * Deletes a gift certificate by its ID.
     *
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
