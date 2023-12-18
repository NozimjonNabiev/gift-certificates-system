package com.epam.esm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Generic response wrapper class to encapsulate HTTP status, message, and content.
 *
 * @param <T> Type of content wrapped in the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    private HttpStatus status;
    private String message;
    private T content;

    /**
     * Constructs a ResponseData object with content, status, and message.
     *
     * @param content The content to be wrapped in the response.
     * @param status  The HTTP status associated with the response.
     * @param message The message associated with the response.
     */
    public ResponseData(T content, HttpStatus status, String message) {
        this.content = content;
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a ResponseData object with status and message (without content).
     *
     * @param status  The HTTP status associated with the response.
     * @param message The message associated with the response.
     */
    public ResponseData(HttpStatus status, String message) {
        this.content = null;
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a ResponseData object with only content.
     *
     * @param content The content to be wrapped in the response.
     */
    public ResponseData(T content) {
        this.content = content;
    }
}
