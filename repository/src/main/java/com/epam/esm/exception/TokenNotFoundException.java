package com.epam.esm.exception;

import javax.naming.AuthenticationException;

/**
 * Custom exception class for handling token not found scenarios.
 * This exception is thrown when the access token for a specific user cannot be found.
 * Extends {@link javax.naming.AuthenticationException}.
 *
 */
public class TokenNotFoundException extends AuthenticationException {

    /**
     * Constructs a {@code TokenNotFoundException} with the specified username.
     *
     * @param username The username for which the access token is not found.
     */
    public TokenNotFoundException(String username) {
        super("Failed to find access token for " + username);
    }
}
