package com.epam.esm.util;

import org.springframework.http.HttpStatus;

/**
 * ErrorResponse is a record representing an error response with a message, error code, and HTTP status.
 *
 * @param errorMessage The error message describing the issue.
 * @param errorCode    The error code associated with the issue.
 * @param httpStatus   The HTTP status indicating the response status.
 */
public record ErrorResponse(
        String errorMessage,
        int errorCode,
        HttpStatus httpStatus
) {
}
