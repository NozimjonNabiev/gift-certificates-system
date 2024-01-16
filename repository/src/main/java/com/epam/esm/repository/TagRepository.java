package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Pagination;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for managing {@link Tag} entities.
 *
 */
public interface TagRepository extends BaseRepository<Tag> {

    /**
     * Retrieves a list of tags based on pagination parameters.
     *
     * @param pagination The pagination information.
     * @return A list of tags matching the pagination criteria.
     */
    List<Tag> findAllByPage(Pagination pagination);

    /**
     * Retrieves a tag by its unique identifier.
     *
     * @param id The unique identifier of the tag.
     * @return The tag with the given ID.
     */
    Tag findById(Long id);

    /**
     * Retrieves a tag by its name.
     *
     * @param name The name of the tag.
     * @return An {@link Optional} containing the tag if found by name, otherwise empty.
     */
    Optional<Tag> findByName(String name);

    /**
     * Retrieves a special tag.
     *
     * @return The special tag.
     */
    Tag findMostUsedTagOfUserWithHighestOrderCost(Long userId);

    /**
     * Saves a new or updates an existing tag.
     *
     * @param tag The tag to save or update.
     */
    void save(Tag tag);

    /**
     * Associates tags with a gift certificate.
     *
     * @param giftCertificate The gift certificate to associate with the tags.
     * @param tags            The set of tags to associate with the gift certificate.
     */
    void setTags(GiftCertificate giftCertificate, Set<Tag> tags);

    /**
     * Deletes a tag.
     *
     * @param tag The tag to delete.
     */
    void delete(Tag tag);
}
