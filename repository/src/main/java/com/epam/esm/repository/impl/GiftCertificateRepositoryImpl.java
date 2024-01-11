package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.util.mapper.GiftCertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static com.epam.esm.util.DatabaseQueryConstants.*;

/**
 * Repository implementation for handling operations related to Gift Certificates in the database.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;
    private final GiftCertificateRowMapper giftCertificateRowMapper;

    /**
     * Retrieves all Gift Certificates from the database.
     *
     * @return List of all GiftCertificate objects.
     */
    @Override
    public List<GiftCertificate> findAll() {
        log.info("Querying all rows from gift certificate table...");
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES, giftCertificateRowMapper);
        giftCertificates.forEach(this::setAllTags);
        return giftCertificates;
    }

    /**
     * Finds a Gift Certificate by its ID.
     *
     * @param id The ID of the Gift Certificate to find.
     * @return Optional containing the GiftCertificate if found, otherwise empty.
     */
    @Override
    public Optional<GiftCertificate> findById(Long id) {
        log.info("Querying a row from gift certificate table by id...");
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(FIND_GIFT_CERTIFICATE_BY_ID, giftCertificateRowMapper, id);
        if (giftCertificate == null) {
            return Optional.empty();
        }
        setAllTags(giftCertificate);
        return Optional.of(giftCertificate);
    }

    /**
     * Finds Gift Certificates associated with a specific tag.
     *
     * @param tag The tag for which Gift Certificates are to be found.
     * @return List of GiftCertificate objects associated with the tag.
     */
    @Override
    public List<GiftCertificate> findByTag(Tag tag) {
        log.info("Querying rows from gift certificate table by tag...");
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_GIFT_CERTIFICATES_BY_TAG, giftCertificateRowMapper, tag.getName());
        giftCertificates.forEach(this::setAllTags);
        return giftCertificates;
    }

    /**
     * Finds Gift Certificates based on a specified search filter.
     *
     * @param searchFilter The search filter to apply.
     * @return List of GiftCertificate objects matching the search filter.
     */
    @Override
    public List<GiftCertificate> findBySearchFilter(SearchFilter searchFilter) {
        log.info("Querying rows from gift certificate table by search filter...");
        String searchType = searchFilter.getSearchType().getFieldName();
        String searchValue = searchFilter.getSearchPlace().getFormattedValue(searchFilter.getSearchValue());
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(String.format(FIND_GIFT_CERTIFICATES_BY_TYPE, searchType, searchValue), giftCertificateRowMapper);
        giftCertificates.forEach(this::setAllTags);
        return giftCertificates;
    }

    /**
     * Finds Gift Certificates based on a specified sort filter.
     *
     * @param sortFilter The sort filter to apply.
     * @return List of GiftCertificate objects sorted according to the sort filter.
     */
    @Override
    public List<GiftCertificate> findBySortFilter(SortFilter sortFilter) {
        log.info("Querying rows from gift certificate table by sort filter...");
        String sortType = sortFilter.getSortType().getFieldType();
        String sortOrder = sortFilter.getSortOrder().getOrderValue();
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(String.format(FIND_ALL_GIFT_CERTIFICATES_SORT_BY_TYPE_AND_VALUE, sortType, sortOrder), giftCertificateRowMapper);
        giftCertificates.forEach(this::setAllTags);
        return giftCertificates;
    }

    /**
     * Inserts a new Gift Certificate into the database.
     *
     * @param giftCertificate The Gift Certificate to be inserted.
     * @return The ID of the newly inserted Gift Certificate.
     */
    @Override
    public Long insert(GiftCertificate giftCertificate) {
        log.info("Adding new row into gift certificate table...");
        return jdbcTemplate.queryForObject(INSERT_GIFT_CERTIFICATE, Long.class,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());
    }

    /**
     * Updates an existing Gift Certificate in the database.
     *
     * @param giftCertificate The Gift Certificate to be updated.
     */
    @Override
    public void update(GiftCertificate giftCertificate) {
        log.info("Updating row in gift certificate table...");
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getId());
        Integer count = jdbcTemplate.queryForObject(FIND_GIFT_CERTIFICATE_TAG_BY_ID, Integer.class, giftCertificate.getId());
        if (count != null && count > 0) {
            jdbcTemplate.update(DELETE_ALL_GIFT_CERTIFICATE_TAGS, giftCertificate.getId());
        }
    }

    /**
     * Inserts tags associated with a Gift Certificate into the database.
     *
     * @param giftCertificate The Gift Certificate to which tags will be associated.
     */
    @Override
    public void insertTags(GiftCertificate giftCertificate) {
        log.info("Adding new rows into gift certificate_tag table...");
        giftCertificate.getTags().forEach(tag -> jdbcTemplate.update(INSERT_TAG_TO_GIFT_CERTIFICATE, giftCertificate.getId(), tag.getId()));
        setAllTags(giftCertificate);
    }

    /**
     * Deletes a Gift Certificate from the database based on its ID.
     *
     * @param id The ID of the Gift Certificate to delete.
     */
    @Override
    public void delete(Long id) {
        log.info("Deleting row from gift certificate table...");
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
    }

    /**
     * Sets all associated tags for a given Gift Certificate.
     *
     * @param giftCertificate The GiftCertificate object to which tags will be associated.
     */
    private void setAllTags(GiftCertificate giftCertificate) {
        giftCertificate.setTags(new TreeSet<>(jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_TAGS, tagRowMapper, giftCertificate.getId())));
    }
}
