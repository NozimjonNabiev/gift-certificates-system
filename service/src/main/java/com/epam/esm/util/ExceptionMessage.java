package com.epam.esm.util;

/**
 * Enum for error messages related to exceptions
 */
public enum ExceptionMessage {
    GIFT_CERTIFICATE_ID_NOT_FOUND("Gift certificate with ID %s not found"),
    GIFT_CERTIFICATE_UPDATE_FAILED("Failed to update the gift certificate"),
    GIFT_CERTIFICATE_CREATE_FAILED("Failed to create the gift certificate"),
    GIFT_CERTIFICATE_DELETE_FAILED("Failed to delete the gift certificate"),
    GIFT_CERTIFICATES_NOT_FOUND("No gift certificates found"),

    TAG_ID_NOT_FOUND("Tag with ID %s not found"),
    TAG_CREATE_FAILED("Failed to create the tag"),
    TAG_DELETE_FAILED("Failed to delete the tag"),
    TAGS_NOT_FOUND("No tags found");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
