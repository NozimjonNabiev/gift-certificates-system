package com.epam.esm.exception;

/**
 * Custom exception indicating an invalid request body with associated violations.
 */
public class InvalidRequestBodyException extends RuntimeException {

    private final Object[] violations;

    /**
     * Constructs an InvalidRequestBodyException with the specified violations.
     *
     * @param violations The array of violations indicating issues in the request body.
     */
    public InvalidRequestBodyException(Object[] violations) {
        this.violations = violations.clone();
    }

    /**
     * Retrieves the array of violations associated with the exception.
     *
     * @return Cloned array of violations.
     */
    public Object[] getViolations() {
        return violations.clone();
    }
}
