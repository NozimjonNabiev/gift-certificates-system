package com.epam.esm.exception;

/**
 * Custom exception class for handling username not found scenarios.
 * This exception is thrown when a user with a specific username cannot be found.
 * Extends {@link org.springframework.security.core.userdetails.UsernameNotFoundException}.
 *
 */
public class UsernameNotFoundException extends org.springframework.security.core.userdetails.UsernameNotFoundException {

    /**
     * Constructs a {@code UsernameNotFoundException} with the specified username.
     *
     * @param username The username for which the user is not found.
     */
    public UsernameNotFoundException(String username) {
        super("Failed to find user with username " + username);
    }
}
