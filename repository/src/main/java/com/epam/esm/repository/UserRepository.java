package com.epam.esm.repository;

import com.epam.esm.entity.User;
import com.epam.esm.util.Pagination;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> adc8c58a1cab180da67dc263d51083f96b0abfa8

/**
 * Repository interface for managing {@link User} entities.
 *
 */
public interface UserRepository extends BaseRepository<User> {

    /**
     * Retrieves a list of users based on pagination parameters.
     *
     * @param pagination The pagination information.
     * @return A list of users matching the pagination criteria.
     */
    List<User> findAllByPage(Pagination pagination);

    /**
     * Retrieves a user by its unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user with the given ID.
     */
    User findById(Long id);
<<<<<<< HEAD

    /**
     * Saves a new user to the database.
     *
     * @param user The user to be saved.
     * @return The saved user entity.
     */
    User saveUser(User user);

    /**
     * Retrieves a list of users whose password does not match the specified regex pattern.
     *
     * @param regex The regex pattern for password matching.
     * @return A list of users whose password does not match the given regex.
     */
    List<User> findByPasswordNotMatchingRegex(String regex);

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the user.
     * @return An optional containing the user with the given username, if found.
     */
    Optional<User> findByUsername(String username);
=======
>>>>>>> adc8c58a1cab180da67dc263d51083f96b0abfa8
}
