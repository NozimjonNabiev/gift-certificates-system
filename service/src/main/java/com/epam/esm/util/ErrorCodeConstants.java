package com.epam.esm.util;

/**
 * ErrorCodeConstants class provides constant values for error codes used in the application.
 */
public final class ErrorCodeConstants {
    public static final int NOT_FOUND_ERROR_CODE = 404001;
    public static final int METHOD_NOT_ALLOWED_ERROR_CODE = 405001;
    public static final int BAD_REQUEST_ERROR_CODE = 400001;
    public static final int INTERNAL_SERVER_ERROR_CODE = 500001;
    public static final int ACCESS_DENIED_ERROR_CODE = 1000001;
    public static final int AUTHENTICATION_FAILED_ERROR_CODE = 1000005;

    private ErrorCodeConstants() {
    }
}

