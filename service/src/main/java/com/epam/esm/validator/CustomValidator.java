package com.epam.esm.validator;

import com.epam.esm.exception.ValidationException;
import com.epam.esm.util.FieldName;
import lombok.experimental.UtilityClass;

/**
 * Utility class for validating user input details.
 */
@UtilityClass
public class CustomValidator {
    private static final String ONLY_LETTERS_AND_SPACES = "^[a-zA-Z ]*$";

    /**
     * Validates ID parameter to ensure that it is not null and is positive.
     *
     * @param fieldName Field to validate.
     * @param id        Long ID to validate.
     * @throws ValidationException if ID parameter is not valid.
     */
    public void validateId(FieldName fieldName, Long id) {
        notNull(fieldName, id);

        if (id <= 0) {
            throw new ValidationException(fieldName.getName() + " must be positive");
        }
    }

    public void notNull(FieldName fieldName, Object value) {
        if (value == null) {
            throw new ValidationException(fieldName.getName() + " should not be null");
        }
    }

    public void notBlank(FieldName fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName.getName() + " should not be empty or blank");
        }
    }

    public void onlyLettersAndSpaces(FieldName fieldName, String value) {
        if (value == null || !value.matches(ONLY_LETTERS_AND_SPACES)) {
            throw new ValidationException(fieldName.getName() + " must include only letters and spaces");
        }
    }

    public void notTooShortOrLong(FieldName fieldName, String value, int lower, int upper) {
        if (value == null || value.length() < lower || value.length() > upper) {
            throw new ValidationException(fieldName.getName() + " must be between " + lower + " and " + upper + " characters");
        }
    }

    public void notTooLowOrHigh(FieldName fieldName, Double value, int lower, int upper) {
        if (value == null || value < lower || value > upper) {
            throw new ValidationException(fieldName.getName() + " must be between " + lower + " and " + upper);
        }
    }

    public void notNegative(FieldName fieldName, Double value) {
        if (value == null || value < 0) {
            throw new ValidationException(fieldName.getName() + " should not be negative");
        }
    }
}
