package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataModificationException extends RuntimeException {
    public DataModificationException(ExceptionMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
