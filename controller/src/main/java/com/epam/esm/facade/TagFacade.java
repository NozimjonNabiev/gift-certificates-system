package com.epam.esm.facade;

import com.epam.esm.dto.TagDTO;

import java.util.List;

/**
 * The {@code TagFacade} interface extends the {@link BaseFacade} interface
 * and provides additional operations specific to tags. It includes methods
 * for finding all tags with pagination and finding a tag by its ID.
 *
 */
public interface TagFacade extends BaseFacade<TagDTO> {

    /**
     * Retrieves all tags with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link TagDTO}.
     */
    @Override
    List<TagDTO> findAllByPage(int page, int size);

    /**
     * Retrieves a tag by its ID.
     *
     * @param id The ID of the tag.
     * @return The {@link TagDTO} for the specified ID.
     */
    @Override
    TagDTO findById(Long id);
}
