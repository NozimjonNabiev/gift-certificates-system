package com.epam.esm.facade;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;

import java.util.List;

/**
 * The {@code UserFacade} interface extends the {@link BaseFacade} interface
 * and provides additional operations specific to users. It includes methods
 * for finding all users with pagination, finding a user by its ID, and finding
 * the most used tag of a user with the highest order cost.
 *
 */
public interface UserFacade extends BaseFacade<UserDTO> {

    /**
     * Retrieves all users with pagination.
     *
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of {@link UserDTO}.
     */
    @Override
    List<UserDTO> findAllByPage(int page, int size);

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user.
     * @return The {@link UserDTO} for the specified ID.
     */
    @Override
    UserDTO findById(Long id);

    /**
     * Finds the most used tag of a user with the highest order cost.
     *
     * @param userId The ID of the user.
     * @return The {@link TagDTO} representing the most used tag.
     */
    TagDTO findMostUsedTagOfUserWithHighestOrderCost(Long userId);
}
