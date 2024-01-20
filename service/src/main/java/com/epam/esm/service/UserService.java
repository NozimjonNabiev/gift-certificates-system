package com.epam.esm.service;

import com.epam.esm.dto.TokenDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;

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

    /**
     * Signs up a user and returns the corresponding access token.
     *
     * @param user The user to sign up.
     * @return The access token for the signed-up user.
     */
    TokenDTO signUp(User user);

    /**
     * Signs in a user and returns the corresponding access token.
     *
     * @param user The user to sign in.
     * @return The access token for the signed-in user.
     */
    TokenDTO signIn(User user);
}
