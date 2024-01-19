package com.epam.esm.repository;

import com.epam.esm.entity.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Token} entities.
 *
 * @see BaseRepository
 */
public interface TokenRepository extends BaseRepository<Token> {

    /**
     * Retrieves a list of tokens associated with a specific user based on pagination parameters.
     *
     * @param userId The unique identifier of the user.
     * @param page   The page number for pagination.
     * @param size   The number of items per page for pagination.
     * @return A list of tokens associated with the user matching the pagination criteria.
     */
    List<Token> findByUserId(Long userId, int page, int size);

    /**
     * Retrieves a list of tokens created before the specified date and time.
     *
     * @param createdAt The date and time threshold for token creation.
     * @return A list of tokens created before the specified date and time.
     */
    List<Token> findByCreatedAtBefore(LocalDateTime createdAt);

    /**
     * Retrieves a token by its access token.
     *
     * @param accessToken The access token for the token.
     * @return An optional containing the token with the given access token, if found.
     */
    Optional<Token> findByAccessToken(String accessToken);

    /**
     * Saves a new token to the database.
     *
     * @param token The token to be saved.
     * @return The saved token entity.
     */
    Token saveToken(Token token);
}
