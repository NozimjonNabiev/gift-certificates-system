package com.epam.esm.repository;

import com.epam.esm.entity.Identifiable;
import com.epam.esm.util.Pagination;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

/**
 * Generic repository interface for entities implementing {@link Identifiable}.
 *
 * @param <T> Type of entity that extends {@link Identifiable}.
 *
 */
public interface BaseRepository<T extends Identifiable> {

    /**
     * Retrieves a list of entities based on pagination parameters.
     *
     * @param pagination The pagination information to specify the subset of entities to retrieve.
     * @return A list of entities matching the criteria defined by the provided {@link Pagination} object.
     */
    List<T> findAllByPage(Pagination pagination);

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity.
     * @return The entity with the given ID.
     */
    T findById(Long id);

    /**
     * Saves a new entity or updates an existing one.
     *
     * @param entity The entity to be saved or updated.
     */
    default void save(T entity) {
        throw new NotImplementedException("Save method not implemented in this repository");
    }

    /**
     * Deletes an entity from the repository.
     *
     * @param entity The entity to be deleted from the repository.
     */
    default void delete(T entity) {
        throw new NotImplementedException("Delete method not implemented in this repository");
    }
}
