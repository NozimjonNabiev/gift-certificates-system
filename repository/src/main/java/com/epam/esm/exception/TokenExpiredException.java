package com.epam.esm.exception;

import javax.naming.AuthenticationException;

/**
 * Custom exception class for handling expired access tokens.
 * This exception is thrown when the access token for a specific user has expired.
 * Extends {@link javax.naming.AuthenticationException}.
 *
 */
public class TokenExpiredException extends AuthenticationException {

    /**
     * Constructs a {@code TokenExpiredException} with the specified username.
     *
     * @param username The username for which the access token is expired.
     */
    public TokenExpiredException(String username) {
        super("Access token for user " + username + " is expired");
    }
}
