package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.util.Pagination;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link OrderRepository} interface.
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @inheritDoc
     */
    @Override
    public List<Order> findAllByPage(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getLimit());

        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Order findById(Long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find order by id " + id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Order> findByGiftCertificateId(Long giftCertificateId, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("giftCertificate").get("id"), giftCertificateId));

        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getLimit());

        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Order> findByUserId(Long userId, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user").get("id"), userId));

        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getLimit());

        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }
}

