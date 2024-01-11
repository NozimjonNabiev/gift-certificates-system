package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ExceptionMessage;
import com.epam.esm.util.mapper.GiftCertificateMapper;
import com.epam.esm.util.mapper.FilterMapper;
import com.epam.esm.util.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the GiftCertificateService interface that provides business logic related to gift certificates.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final FilterMapper filterMapper;
    private final TagMapper tagMapper;

    /**
     * Retrieves all gift certificates.
     *
     * @return List of all gift certificates in DTO format.
     * @ If no certificates are found.
     */
    @Override
    public List<GiftCertificateDTO> findAll()  {
        try {
            log.info("Finding all certificates...");
            return giftCertificateRepository.findAll().stream()
                    .map(giftCertificateMapper::toGiftCertificateDTO)
                    .toList();
        } catch (DataAccessException ex) {
            log.error("Failed to find certificates, cause: {}", ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATES_NOT_FOUND, ex);
        }
    }

    /**
     * Retrieves a gift certificate by ID.
     *
     * @param id The ID of the gift certificate.
     * @return The gift certificate in DTO format.
     * @ If the certificate with the given ID is not found.
     */
    @Override
    public GiftCertificateDTO findById(Long id)  {
        log.info("Finding certificate by ID: {}", id);
        try {
            GiftCertificate certificate = giftCertificateRepository.findById(id).orElseThrow();
            return giftCertificateMapper.toGiftCertificateDTO(certificate);
        } catch (EmptyResultDataAccessException ex) {
            log.error("Failed to find certificate by ID: {}, cause: {}", id, ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATE_ID_NOT_FOUND, id);
        }
    }

    /**
     * Retrieves gift certificates by tag.
     *
     * @param tag The tag associated with the certificates.
     * @return List of gift certificates in DTO format associated with the tag.
     * @ If no certificates are found for the given tag.
     */
    @Override
    public List<GiftCertificateDTO> findByTag(TagDTO tag)  {
        try {
            log.info("Finding certificates by tag...");
            return giftCertificateRepository.findByTag(tagMapper.toTag(tag)).stream()
                    .map(giftCertificateMapper::toGiftCertificateDTO)
                    .toList();
        } catch (DataAccessException ex) {
            log.error("Failed to find certificates by tag, cause: {}", ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATES_NOT_FOUND, ex);
        }
    }

    /**
     * Retrieves gift certificates by search filters.
     *
     * @param searchFilter The search filters to apply.
     * @return List of gift certificates in DTO format based on the search criteria.
     * @ If no certificates are found based on the search filters.
     */
    @Override
    public List<GiftCertificateDTO> findBySearchFilter(SearchFilterDTO searchFilter)  {
        try {
            log.info("Finding certificates by search filter...");
            return giftCertificateRepository.findBySearchFilter(filterMapper.toSearchFilter(searchFilter)).stream()
                    .map(giftCertificateMapper::toGiftCertificateDTO)
                    .toList();
        } catch (DataAccessException ex) {
            log.error("Failed to find certificates by search filter, cause: {}", ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATES_NOT_FOUND, ex);
        }
    }

    /**
     * Retrieves gift certificates by sort filters.
     *
     * @param sortFilter The sort filters to apply.
     * @return List of gift certificates in DTO format based on the sort criteria.
     * @ If no certificates are found based on the sort filters.
     */
    @Override
    public List<GiftCertificateDTO> findBySortFilter(SortFilterDTO sortFilter)  {
        try {
            log.info("Finding certificates by sort filter...");
            return giftCertificateRepository.findBySortFilter(filterMapper.toSortFilter(sortFilter)).stream()
                    .map(giftCertificateMapper::toGiftCertificateDTO)
                    .toList();
        } catch (DataAccessException ex) {
            log.error("Failed to find certificates by sort filter, cause: {}", ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATES_NOT_FOUND, ex);
        }
    }

    /**
     * Creates a new gift certificate.
     *
     * @param giftCertificateDTO The gift certificate data to create.
     * @ If creation of the certificate fails.
     */
    @Override
    public void create(GiftCertificateDTO giftCertificateDTO)  {
        GiftCertificate certificate = giftCertificateMapper.toGiftCertificate(giftCertificateDTO);
        try {
            log.info("Creating new certificate...");
            certificate.setId(giftCertificateRepository.insert(certificate));
            insertTags(certificate, giftCertificateDTO.getTags());
        } catch (DataAccessException ex) {
            log.error("Failed to create certificate, cause: {}", ex.getMessage());
            throw new DataModificationException(ExceptionMessage.GIFT_CERTIFICATE_CREATE_FAILED, ex);
        }
    }

    /**
     * Inserts tags associated with a gift certificate into the database.
     *
     * @param giftCertificate The gift certificate to associate tags with.
     * @param tags            The list of tags to associate with the gift certificate.
     */
    private void insertTags(GiftCertificate giftCertificate, List<TagDTO> tags) {
        tags.forEach(tag -> tag.setId(tagRepository.insert(tagMapper.toTag(tag))));
        giftCertificate.setTags(tags.stream()
                .map(tagMapper::toTag)
                .collect(Collectors.toSet()));
        giftCertificateRepository.insertTags(giftCertificate);
    }

    /**
     * Updates an existing gift certificate.
     *
     * @param giftCertificateDTO The gift certificate data to update.
     * @        If the certificate to update is not found.
     * @ If updating the certificate fails.
     */
    @Override
    public void update(GiftCertificateDTO giftCertificateDTO) {
        Long id = giftCertificateDTO.getId();
        try {
            log.info("Editing certificate...");
            giftCertificateRepository.findById(id);
            GiftCertificate certificate = giftCertificateMapper.toGiftCertificate(giftCertificateDTO);
            giftCertificateRepository.update(certificate);
            giftCertificateDTO.getTags().forEach(tag -> tag.setId(tagRepository.insert(tagMapper.toTag(tag))));
            certificate.setTags(giftCertificateDTO.getTags().stream()
                    .map(tagMapper::toTag)
                    .collect(Collectors.toSet()));
            giftCertificateRepository.insertTags(certificate);
        } catch (EmptyResultDataAccessException ex) {
            log.error("Failed to find certificate by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATE_ID_NOT_FOUND, id);
        } catch (DataAccessException ex) {
            log.error("Failed to update certificate with id {}, cause {}", id, ex.getMessage());
            throw new DataModificationException(ExceptionMessage.GIFT_CERTIFICATE_UPDATE_FAILED, ex);
        }
    }

    /**
     * Deletes a gift certificate by ID.
     *
     * @param id The ID of the certificate to delete.
     * @        If the certificate to delete is not found.
     * @ If deletion of the certificate fails.
     */
    @Override
    public void delete(Long id) {
        try {
            log.info("Deleting certificate...");
            giftCertificateRepository.findById(id);
            giftCertificateRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("failed to find certificate by id {}, cause {}", id, ex.getMessage());
            throw new NotFoundException(ExceptionMessage.GIFT_CERTIFICATE_ID_NOT_FOUND, id);
        } catch (DataAccessException ex) {
            log.error("failed to delete certificate with id {}, cause {}", id, ex.getMessage());
            throw new DataModificationException(ExceptionMessage.GIFT_CERTIFICATE_DELETE_FAILED, ex);
        }
    }
}
