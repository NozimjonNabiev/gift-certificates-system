package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.util.SearchFilter;

import java.util.List;

/**
 * Service interface for managing {@link GiftCertificateDTO} entities.
 *
 */
public interface GiftCertificateService {

    /**
     * Retrieves a list of gift certificates based on pagination parameters.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return A list of gift certificates matching the pagination criteria.
     */
    List<GiftCertificateDTO> findAllByPage(int page, int size);

    /**
     * Retrieves a gift certificate by its unique identifier.
     *
     * @param id The unique identifier of the gift certificate.
     * @return The gift certificate with the given ID.
     */
    GiftCertificateDTO findById(Long id);

    /**
     * Retrieves a list of gift certificates based on a search filter and pagination parameters.
     *
     * @param searchFilter The search filter criteria to filter gift certificates.
     * @param page         The page number.
     * @param size         The size of each page.
     * @return A list of gift certificates matching the search filter and pagination criteria.
     */
    List<GiftCertificateDTO> findByFilterAndPage(SearchFilter searchFilter, int page, int size);

    /**
     * Updates an existing gift certificate.
     *
     * @param id                   The unique identifier of the gift certificate to update.
     * @param giftCertificateDTO The updated gift certificate data.
     * @return The updated gift certificate.
     */
    GiftCertificateDTO update(Long id, GiftCertificateDTO giftCertificateDTO);

    /**
     * Updates the price of a gift certificate by its unique identifier.
     *
     * @param id                   The unique identifier of the gift certificate to update.
     * @param giftCertificateDTO The updated gift certificate data containing the new price.
     * @return The updated gift certificate.
     */
    GiftCertificateDTO updatePriceById(Long id, GiftCertificateDTO giftCertificateDTO);

    /**
     * Creates a new gift certificate.
     *
     * @param giftCertificateDTO The gift certificate data to create.
     * @return The created gift certificate.
     */
    GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO);

    /**
     * Deletes a gift certificate by its unique identifier.
     *
     * @param id The unique identifier of the gift certificate to delete.
     */
    void deleteById(Long id);
}
