package com.epam.esm.validator;

import com.epam.esm.exception.ValidationException;
import com.epam.esm.util.enums.PaginationField;
import lombok.experimental.UtilityClass;

/**
 * Utility class for validating gift certificate pagination details.
 */
@UtilityClass
public class PaginationValidator {
    /**
     * Validates pagination parameters for a search operation to ensure that
     * they contain only digits, are not negative, and are between upper and lower bounds.
     *
     * @param page int page number to validate.
     * @param size int page size to validate.
     * @throws ValidationException if any of pagination parameters are not valid.
     */
    public void validate(int page, int size) {
        validatePage(page);
        validateSize(size);
    }

    void validatePage(int page) {
        CustomValidator.notNegative(PaginationField.NUMBER, (double) page);
        CustomValidator.notTooLowOrHigh(PaginationField.NUMBER, (double) page, 0, 10000);
    }

    void validateSize(int size) {
        CustomValidator.notNegative(PaginationField.SIZE, (double) size);
        CustomValidator.notTooLowOrHigh(PaginationField.SIZE, (double) size, 0, 100);
    }
}
