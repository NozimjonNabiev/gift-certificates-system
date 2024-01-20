package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

/**
 * Service interface for managing {@link TagDTO} entities.
 *
 */
public interface TagService {

    /**
     * Retrieves a list of tags based on pagination parameters.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return A list of tags matching the pagination criteria.
     */
    List<TagDTO> findAllByPage(int page, int size);

    /**
     * Retrieves a tag by its unique identifier.
     *
     * @param id The unique identifier of the tag.
     * @return The tag with the given ID.
     */
    TagDTO findById(Long id);

    /**
     * Retrieves the most used tag of a user with the highest order cost.
     *
     * @param userId The unique identifier of the user.
     * @return The most used tag of the user with the highest order cost.
     */
    List<TagDTO> findMostUsedTagOfUserWithHighestOrderCost(Long userId);

    /**
     * Creates a new tag.
     *
     * @param tagDTO The tag data to create.
     * @return The created tag.
     */
    TagDTO create(TagDTO tagDTO);

    /**
     * Deletes a tag by its unique identifier.
     *
     * @param id The unique identifier of the tag to delete.
     */
    void deleteById(Long id);
}
