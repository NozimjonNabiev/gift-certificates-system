package com.epam.esm.repository;

import com.epam.esm.entity.User;
import com.epam.esm.util.Pagination;

import java.util.List;

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
}
