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
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }
}
