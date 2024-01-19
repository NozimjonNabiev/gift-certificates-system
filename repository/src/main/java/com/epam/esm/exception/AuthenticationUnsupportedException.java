package com.epam.esm.exception;

import org.springframework.security.core.AuthenticationException;
/**
 * Custom exception class for handling unsupported authentication types.
 * This exception is thrown when authentication is not supported for a specific class or type.
 * Extends {@link org.springframework.security.core.AuthenticationException}.
 *
 */
public class AuthenticationUnsupportedException extends AuthenticationException {

    /**
     * Constructs an {@code AuthenticationUnsupportedException} with the specified class.
     *
     * @param aClass The class for which authentication is not supported.
     */
    public AuthenticationUnsupportedException(Class<?> aClass) {
        super("Authentication not supported for " + aClass);
    }
}
