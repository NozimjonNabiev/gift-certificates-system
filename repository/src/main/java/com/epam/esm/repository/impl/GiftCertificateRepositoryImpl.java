package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.SearchFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Implementation of the {@link GiftCertificateRepository} interface.
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Map<String, Comparator<GiftCertificate>> giftCertificateComparators = Map.of(
            "id", Comparator.comparing(GiftCertificate::getId),
            "name", Comparator.comparing(GiftCertificate::getName),
            "description", Comparator.comparing(GiftCertificate::getDescription),
            "price", Comparator.comparing(GiftCertificate::getPrice),
            "duration", Comparator.comparing(GiftCertificate::getDuration),
            "createDate", Comparator.comparing(GiftCertificate::getCreateDate),
            "lastUpdateDate", Comparator.comparing(GiftCertificate::getLastUpdateDate)
    );

    /**
     * @inheritDoc
     */
    @Override
    public List<GiftCertificate> findAllByPage(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(root);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getLimit());

        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public GiftCertificate findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id))
                .orElseThrow(() -> new EntityNotFoundException("Failed to find giftCertificate by id " + id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<GiftCertificate> findByFilterAndPage(SearchFilter searchFilter, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        Predicate finalPredicate = criteriaBuilder.conjunction();
        List<Predicate> tagPredicates = new ArrayList<>();

        // Filtering by tags
        if (searchFilter.tags() != null && !searchFilter.tags().isEmpty()) {
            Join<GiftCertificate, Tag> tagJoin = root.join("getTags()");
            for (Tag tag : searchFilter.tags()) {
                Predicate tagPredicate = criteriaBuilder.equal(tagJoin.get("name"), tag.getName());
                tagPredicates.add(tagPredicate);
            }
            finalPredicate = criteriaBuilder.and(tagPredicates.toArray(new Predicate[0]));
        }

        // Filtering by name and description
        if (!searchFilter.name().isEmpty() || !searchFilter.description().isEmpty()) {
            Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%" + searchFilter.name() + "%"),
                    criteriaBuilder.like(root.get("description"), "%" + searchFilter.description() + "%")
            );
            finalPredicate = criteriaBuilder.and(finalPredicate, searchPredicate);
        }

        // Sorting
        if (!searchFilter.sortType().isEmpty()) {
            Comparator<GiftCertificate> comparator = giftCertificateComparators.get(searchFilter.sortType());
            if (comparator != null) {
                List<GiftCertificate> certificates = entityManager.createQuery(criteriaQuery).getResultList();
                certificates.removeIf(giftCertificate -> !giftCertificate.getTags().containsAll(searchFilter.tags()));
                certificates.sort(searchFilter.isDescending() ? comparator.reversed() : comparator);
                return certificates;
            }
        }

        criteriaQuery.where(finalPredicate);

        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
    }

    /**
     * @inheritDoc
     */
    @Override
<<<<<<< HEAD
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
=======
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
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
    }
}
