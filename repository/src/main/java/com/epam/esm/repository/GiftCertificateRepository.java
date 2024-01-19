package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.SearchFilter;

import java.util.List;

/**
 * Repository interface for managing {@link GiftCertificate} entities.
 *
 */
public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {

    /**
     * Retrieves a list of gift certificates based on pagination parameters.
     *
     * @param pagination The pagination information to specify the subset of gift certificates to retrieve.
     * @return A list of gift certificates matching the criteria defined by the provided {@link Pagination} object.
     */
    List<GiftCertificate> findAllByPage(Pagination pagination);

    /**
     * Retrieves a gift certificate by its unique identifier.
     *
     * @param id The unique identifier of the gift certificate.
     * @return The gift certificate with the given ID.
     */
    GiftCertificate findById(Long id);

    /**
     * Retrieves a list of gift certificates based on a search filter and pagination parameters.
     *
     * @param searchFilter The search filter criteria to filter gift certificates.
     * @param pagination   The pagination information to specify the subset of gift certificates to retrieve.
     * @return A list of gift certificates matching the search filter and pagination criteria.
     */
    List<GiftCertificate> findByFilterAndPage(SearchFilter searchFilter, Pagination pagination);

    /**
     * Saves a new or updates an existing gift certificate.
     *
     * @param giftCertificate The gift certificate to be saved or updated.
     */
    void save(GiftCertificate giftCertificate);

    /**
     * Deletes a gift certificate from the repository.
     *
     * @param giftCertificate The gift certificate to be deleted from the repository.
     */
    void delete(GiftCertificate giftCertificate);
}
