package com.epam.esm.exception;

import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.util.constants.ErrorCodeConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

/**
 * {@code GlobalExceptionHandler} is a controller advice class responsible for handling
 * exceptions globally in the application. It extends {@link ResponseEntityExceptionHandler}
 * to provide custom exception handling for various scenarios.
 *
 * <p>This class includes methods to handle specific exceptions such as
 * {@link NoHandlerFoundException}, {@link HttpRequestMethodNotSupportedException}, and
 * {@link MethodArgumentTypeMismatchException}. Additionally, it handles custom exceptions
 * like {@link ValidationException}, {@link EntityNotFoundException}, and
 * {@link EntityAlreadyExistsException}.
 *
 * <p>The class is annotated with {@link ControllerAdvice} and {@link RequiredArgsConstructor},
 * indicating that it is designed to be used across all controllers and requires constructor
 * injection of a {@link HateoasAdder} for adding HATEOAS links to the response entities.
 *
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link NoHandlerFoundException} by returning a custom error message with
     * appropriate status and HATEOAS links.
     *
     * @param ex      The exception instance.
     * @param headers The headers for the response.
     * @param status  The HTTP status for the response.
     * @param request The WebRequest instance.
     * @return A ResponseEntity containing the error message and links.
     */
    @Override
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        String errorMessage = "No handler found for "
                + ex.getHttpMethod() + " " + ex.getRequestURL();
        MessageHolder messageHolder = new MessageHolder(
                HttpStatus.NOT_FOUND, errorMessage, ErrorCodeConstants.NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(messageHolder, headers, status);
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException} by returning a custom error
     * message with appropriate status and HATEOAS links.
     *
     * @param ex      The exception instance.
     * @param headers The headers for the response.
     * @param status  The HTTP status for the response.
     * @param request The WebRequest instance.
     * @return A ResponseEntity containing the error message and links.
     */
    @Override
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        StringBuilder errorMessage = new StringBuilder(ex.getMethod());
        errorMessage.append(" method is not supported for this request. Supported methods are: ");
        Objects.requireNonNull(ex.getSupportedHttpMethods())
                .forEach(method -> errorMessage.append(method).append(" "));
        MessageHolder messageHolder = new MessageHolder(
                HttpStatus.METHOD_NOT_ALLOWED, errorMessage.toString(), ErrorCodeConstants.METHOD_NOT_ALLOWED_ERROR_CODE);
        return new ResponseEntity<>(messageHolder, headers, status);
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} by returning a custom error
     * message with appropriate status and HATEOAS links.
     *
     * @param ex The exception instance.
     * @return A ResponseEntity containing the error message and links.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageHolder> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        String errorMessage = ex.getName() + " should be of type "
                + Objects.requireNonNull(ex.getRequiredType()).getName();
        MessageHolder messageHolder =
                new MessageHolder(HttpStatus.BAD_REQUEST, errorMessage, ErrorCodeConstants.BAD_REQUEST_ERROR_CODE);
        return new ResponseEntity<>(messageHolder, messageHolder.getStatus());
    }

    /**
     * Handles custom exceptions like {@link ValidationException},
     * {@link EntityNotFoundException}, and {@link EntityAlreadyExistsException}.
     *
     * @param ex The exception instance.
     * @return A ResponseEntity containing the error message and links.
     */
    @ExceptionHandler({
            ValidationException.class,
            EntityNotFoundException.class,
            EntityAlreadyExistsException.class})
    public ResponseEntity<MessageHolder> handleCustomException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Something went wrong :(";
        int errorCode = ErrorCodeConstants.BAD_REQUEST_ERROR_CODE;

        if (ex instanceof ValidationException
                || ex instanceof EntityAlreadyExistsException) {
            message = ex.getMessage();
        }

        if (ex instanceof EntityNotFoundException){
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
            errorCode = ErrorCodeConstants.NOT_FOUND_ERROR_CODE;
        }

        MessageHolder messageHolder = new MessageHolder(status, message, errorCode);
        return new ResponseEntity<>(messageHolder, messageHolder.getStatus());
    }
}
