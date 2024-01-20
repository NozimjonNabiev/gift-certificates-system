package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;

/**
 * {@code MessageHolder} is a class representing a standardized message with status,
 * message, and an optional error code. It extends {@link RepresentationModel} from
 * Spring HATEOAS to support the addition of HATEOAS links to the response.
 *
 * <p>The class is annotated with {@link Data} and {@link EqualsAndHashCode}, providing
 * getters, setters, equals, hashCode, and a toString method. Additionally, it uses
 * {@link JsonInclude} to exclude null fields from the JSON representation.
 *
 * <p>Instances of this class are typically used to encapsulate error responses in a
 * consistent format across the application.
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageHolder extends RepresentationModel<MessageHolder> {
    private final HttpStatus status;
    private final String message;
    private int errorCode;

    /**
     * Constructs a new {@code MessageHolder} with the specified HTTP status and message.
     *
     * @param status  The HTTP status of the response.
     * @param message The message describing the response.
     */
    public MessageHolder(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a new {@code MessageHolder} with the specified HTTP status, message,
     * and error code.
     *
     * @param status    The HTTP status of the response.
     * @param message   The message describing the response.
     * @param errorCode The error code associated with the response.
     */
    public MessageHolder(HttpStatus status, String message, int errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }
}
