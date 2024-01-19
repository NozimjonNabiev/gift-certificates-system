package com.epam.esm.exception;

/**
 * The {@code EntityAlreadyExistsException} class is a runtime exception
 * indicating that an entity already exists in the system when it shouldn't.
 *
 */
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code EntityAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
