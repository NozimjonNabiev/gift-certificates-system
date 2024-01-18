package com.epam.esm.exception;

/**
 * The {@code ValidationException} class is a runtime exception
 * indicating that a validation error has occurred.
 *
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new {@code ValidationException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
