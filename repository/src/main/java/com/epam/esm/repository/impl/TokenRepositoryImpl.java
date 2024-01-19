package com.epam.esm.repository.impl;

import com.epam.esm.entity.Token;
import com.epam.esm.repository.TokenRepository;
import com.epam.esm.util.Pagination;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link TokenRepository} interface.
 */
@Repository
public class TokenRepositoryImpl implements TokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @inheritDoc
     */
    @Override
    public List<Token> findByUserId(Long userId, int page, int size) {
        String jpql = "SELECT t FROM Token t WHERE t.user.id = :userId";
        Query query = entityManager.createQuery(jpql, Token.class);
        query.setParameter("userId", userId);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Token> findByCreatedAtBefore(LocalDateTime createdAt) {
        String jpql = "SELECT t FROM Token t WHERE t.createdAt < :createdAt";
        Query query = entityManager.createQuery(jpql, Token.class);
        query.setParameter("createdAt", createdAt);
        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Token> findByAccessToken(String accessToken) {
        String jpql = "SELECT t FROM Token t WHERE t.accessToken = :accessToken";
        Query query = entityManager.createQuery(jpql, Token.class);
        query.setParameter("accessToken", accessToken);
        List<Token> tokens = query.getResultList();
        return tokens.isEmpty() ? Optional.empty() : Optional.of(tokens.get(0));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Token> findAllByPage(Pagination pagination) {
        String jpql = "SELECT t FROM Token t";
        Query query = entityManager.createQuery(jpql, Token.class);
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getLimit());
        return query.getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Token findById(Long id) {
        return entityManager.find(Token.class, id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Token saveToken(Token token) {
        entityManager.persist(token);
        return token;
    }
}
