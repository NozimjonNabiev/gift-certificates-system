package com.epam.esm.exception;

/**
 * The {@code EntityNotFoundException} class is a runtime exception
 * indicating that an entity was not found when it was expected to exist.
 *
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code EntityNotFoundException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
