package com.epam.esm.exception;

<<<<<<< HEAD
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.util.constants.ErrorCodeConstants;
=======
import com.epam.esm.response.ResponseData;
import com.epam.esm.util.ErrorResponse;
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
=======
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.epam.esm.util.constants.ErrorCodeConstants.*;

>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223

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
    private final HateoasAdder<MessageHolder> messageHolderHateoasAdder;

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
<<<<<<< HEAD
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
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return new ResponseEntity<>(messageHolder, headers, status);
=======
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseData<Object[]> handleInvalidRequestBodyException(InvalidRequestBodyException ex) {
        log.info("Sending error message for invalid request body...");
        return new ResponseData<>(new Object[]{INVALID_REQUEST_BODY_ERROR_CODE, ex.getViolations()}, HttpStatus.BAD_REQUEST, "Invalid request body");
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
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
<<<<<<< HEAD
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
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return new ResponseEntity<>(messageHolder, headers, status);
=======
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseData<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.info("Sending error message for not found exception...");
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), NOT_FOUND_ERROR_CODE, HttpStatus.NOT_FOUND);
        return new ResponseData<>(errorResponse);
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} by returning a custom error
     * message with appropriate status and HATEOAS links.
     *
     * @param ex The exception instance.
     * @return A ResponseEntity containing the error message and links.
     */
<<<<<<< HEAD
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageHolder> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        String errorMessage = ex.getName() + " should be of type "
                + Objects.requireNonNull(ex.getRequiredType()).getName();
        MessageHolder messageHolder =
                new MessageHolder(HttpStatus.BAD_REQUEST, errorMessage, ErrorCodeConstants.BAD_REQUEST_ERROR_CODE);
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
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
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Something went wrong :(";

        if (ex instanceof ValidationException
                || ex instanceof EntityAlreadyExistsException) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        }

        if (ex instanceof EntityNotFoundException){
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }

        MessageHolder messageHolder = new MessageHolder(status, message, ErrorCodeConstants.INTERNAL_SERVER_ERROR_CODE);
        messageHolderHateoasAdder.addLinksToEntity(messageHolder);
        return new ResponseEntity<>(messageHolder, messageHolder.getStatus());
=======
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataModificationException.class)
    public ResponseData<ErrorResponse> handleModificationException(DataModificationException ex) {
        log.info("Sending error message for modification exception...");
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), MODIFICATION_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseData<>(errorResponse);
>>>>>>> a4283a6817df2d4f64063599579969d47ed4d223
    }
}
