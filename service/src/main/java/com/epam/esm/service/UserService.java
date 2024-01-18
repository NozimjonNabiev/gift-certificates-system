package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;

import java.util.List;

/**
 * Service interface for managing {@link UserDTO} entities.
 *
 */
public interface UserService {

    /**
     * Retrieves a list of users based on pagination parameters.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return A list of users matching the pagination criteria.
     */
    List<UserDTO> findAllByPage(int page, int size);

    /**
     * Retrieves a user by its unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user with the given ID.
     */
    UserDTO findById(Long id);
}
