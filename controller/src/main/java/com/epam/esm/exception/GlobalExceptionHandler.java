package com.epam.esm.exception;

import com.epam.esm.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseData<Object[]> handleInvalidRequestBodyException(InvalidRequestBodyException ex) {
        log.info("Sending error message for invalid request body...");
        return new ResponseData<>(ex.getViolations(), HttpStatus.BAD_REQUEST, "Invalid request body");
    }

    /**
     * Handles NotFoundException and returns an appropriate error response.
     *
     * @param ex The NotFoundException instance.
     * @return ResponseData containing details of the error.
     */
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseData<Object> handleNotFoundException(NotFoundException ex) {
        log.info("Sending error message for not found exception...");
        return new ResponseData<>(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles DataModificationException and returns an appropriate error response.
     *
     * @param ex The DataModificationException instance.
     * @return ResponseData containing details of the error.
     */
    @ResponseBody
    @ExceptionHandler(DataModificationException.class)
    public ResponseData<Object> handleModificationException(DataModificationException ex) {
        log.info("Sending error message for modification exception...");
        return new ResponseData<>(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
