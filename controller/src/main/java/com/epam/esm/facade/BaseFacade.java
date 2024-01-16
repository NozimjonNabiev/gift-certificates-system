package com.epam.esm.facade;

import com.epam.esm.exception.MessageHolder;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * The {@code BaseFacade} interface provides the basic CRUD operations for entities
 * represented by classes extending {@link RepresentationModel}. It defines methods
 * for finding all entities with pagination, finding an entity by its ID, creating
 * a new entity, and deleting an entity by its ID.
 *
 * <p>Implementations of this interface are expected to provide concrete
 * implementations for the specified operations.
 *
 * @param <T> The type of the entity extending {@link RepresentationModel}.
 */
public interface BaseFacade<T extends RepresentationModel<T>> {

    /**
     * Retrieves all entities with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of entities of type {@code T}.
     */
    List<T> findAllByPage(int page, int size);

    /**
     * Retrieves an entity by its ID.
     *
     * @param id The ID of the entity.
     * @return The entity of type {@code T} for the specified ID.
     */
    T findById(Long id);

    /**
     * Creates a new entity.
     *
     * @param t The entity of type {@code T} to be created.
     * @return The created entity of type {@code T}.
     */
    default T create(T t) {
        return null;
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to be deleted.
     * @return A {@link MessageHolder} indicating the result of the deletion.
     */
    default MessageHolder deleteById(Long id) {
        return null;
    }
}
