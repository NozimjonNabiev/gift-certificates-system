package com.epam.esm.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Custom exception class for handling authentication failures.
 * This exception is thrown when authentication fails for a specific user.
 * Extends {@link org.springframework.security.core.AuthenticationException}.
 *
 */
public class AuthenticationFailedException extends AuthenticationException {

    /**
     * Constructs an {@code AuthenticationFailedException} with the specified username.
     *
     * @param username The username for which authentication failed.
     */
    public AuthenticationFailedException(String username) {
        super("Authentication failed for " + username);
    }
}
