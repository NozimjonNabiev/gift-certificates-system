package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(ExceptionMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public NotFoundException(ExceptionMessage message, Long id) {
        super(String.format(message.getMessage(), id));
    }
}
