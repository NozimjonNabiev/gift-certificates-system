package com.epam.esm.facade;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.util.SearchFilter;

import java.util.List;

/**
 * The {@code GiftCertificateFacade} interface extends the {@link BaseFacade} interface
 * and provides additional operations specific to gift certificates. It includes methods
 * for finding gift certificates by various criteria, updating a gift certificate, and
 * updating the price of a gift certificate.
 *
 */
public interface GiftCertificateFacade extends BaseFacade<GiftCertificateDTO> {

    /**
     * Retrieves all gift certificates with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link GiftCertificateDTO}.
     */
    @Override
    List<GiftCertificateDTO> findAllByPage(int page, int size);

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate.
     * @return The {@link GiftCertificateDTO} for the specified ID.
     */
    @Override
    GiftCertificateDTO findById(Long id);

    /**
     * Finds gift certificates based on the provided search filter, with pagination.
     *
     * @param searchFilter The {@link SearchFilter} object containing search criteria.
     * @param page         The page number for pagination.
     * @param size         The number of items per page.
     * @return A list of {@link GiftCertificateDTO} matching the search criteria.
     */
    List<GiftCertificateDTO> findByFilter(SearchFilter searchFilter, int page, int size);

    /**
     * Updates a gift certificate by its ID.
     *
     * @param id                   The ID of the gift certificate to update.
     * @param giftCertificateDTO The {@link GiftCertificateDTO} containing the updates.
     * @return The updated {@link GiftCertificateDTO}.
     */
    GiftCertificateDTO update(Long id, GiftCertificateDTO giftCertificateDTO);

    /**
     * Updates the price of a gift certificate by its ID.
     *
     * @param id                   The ID of the gift certificate.
     * @param giftCertificateDTO The {@link GiftCertificateDTO} containing the updated price.
     * @return The updated {@link GiftCertificateDTO}.
     */
    GiftCertificateDTO updatePriceById(Long id, GiftCertificateDTO giftCertificateDTO);
}
