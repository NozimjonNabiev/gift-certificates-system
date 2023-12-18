package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessage;

public class DataModificationException extends Exception {
    public DataModificationException(ExceptionMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
