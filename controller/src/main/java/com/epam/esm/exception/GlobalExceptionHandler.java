package com.epam.esm.exception;

import com.epam.esm.response.ResponseData;
import com.epam.esm.util.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.epam.esm.util.constants.ErrorCodeConstants.*;


/**
 * GlobalExceptionHandler handles exceptions globally for the application.
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * Handles InvalidRequestBodyException and returns an appropriate error response.
     *
     * @param ex The InvalidRequestBodyException instance.
     * @return ResponseData containing details of the error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseData<Object[]> handleInvalidRequestBodyException(InvalidRequestBodyException ex) {
        log.info("Sending error message for invalid request body...");
        return new ResponseData<>(new Object[]{INVALID_REQUEST_BODY_ERROR_CODE, ex.getViolations()}, HttpStatus.BAD_REQUEST, "Invalid request body");
    }

    /**
     * Handles NotFoundException and returns an appropriate error response.
     *
     * @param ex The NotFoundException instance.
     * @return ResponseData containing details of the error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseData<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.info("Sending error message for not found exception...");
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), NOT_FOUND_ERROR_CODE, HttpStatus.NOT_FOUND);
        return new ResponseData<>(errorResponse);
    }

    /**
     * Handles DataModificationException and returns an appropriate error response.
     *
     * @param ex The DataModificationException instance.
     * @return ResponseData containing details of the error.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataModificationException.class)
    public ResponseData<ErrorResponse> handleModificationException(DataModificationException ex) {
        log.info("Sending error message for modification exception...");
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), MODIFICATION_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseData<>(errorResponse);
    }
}
