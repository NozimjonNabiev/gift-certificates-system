package com.epam.esm.repository.impl;

import com.epam.esm.entity.*;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.Pagination;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the {@link TagRepository} interface.
 */
@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @inheritDoc
     */
    @Override
    public List<Tag> findAllByPage(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root);
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getLimit());

        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Tag findById(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return Optional.ofNullable(tag)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find tag by id " + id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream().findFirst();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Tag findMostUsedTagOfUserWithHighestOrderCost(Long userId) {
        String sql = """
           SELECT
               t.tag_id,
               t.name
           FROM
               tags t
               JOIN gift_certificate_tag gct ON t.tag_id = gct.tag_id
               JOIN gift_certificates g ON gct.gift_certificate_id = g.gift_certificate_id
               JOIN orders o ON g.gift_certificate_id = o.gift_certificate_id
           WHERE
               o.user_id = :userId
           GROUP BY
               t.tag_id, t.name
           ORDER BY
               COUNT(o.id) DESC, SUM(o.price) DESC
           LIMIT 1;
           
          """;

        Query nativeQuery = entityManager.createNativeQuery(sql, Tag.class);
        nativeQuery.setParameter("userId", userId);

        return (Tag) nativeQuery.getSingleResult();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save(Tag tag) {
        entityManager.persist(tag);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTags(GiftCertificate giftCertificate, Set<Tag> tags) {
        for (Tag tag : tags) {
            giftCertificate.getTags().add(tag);
        }
        entityManager.merge(giftCertificate);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }
}
