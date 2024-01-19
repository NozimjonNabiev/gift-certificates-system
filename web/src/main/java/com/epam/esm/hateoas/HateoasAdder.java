package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * The {@code HateoasAdder} interface provides methods to add HATEOAS links
 * to entities of type {@link RepresentationModel}. It includes a method for
 * adding links to a single entity and a default method for adding links to a
 * list of entities.
 *
 * @param <T> The type of the entity extending {@link RepresentationModel}.
 */
public interface HateoasAdder<T extends RepresentationModel<T>> {

    /**
     * Adds HATEOAS links to a single entity.
     *
     * @param entity The entity of type {@code T} to which links will be added.
     */
    void addLinksToEntity(T entity);

    /**
     * Adds HATEOAS links to a list of entities.
     *
     * @param entities The list of entities of type {@code T} to which links will be added.
     */
    default void addLinksToEntityList(List<T> entities) {
        entities.forEach(this::addLinksToEntity);
    }
}
