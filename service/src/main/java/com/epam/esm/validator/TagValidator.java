package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.util.enums.field.TagField;
import lombok.experimental.UtilityClass;

/**
 * Utility class for validating gift certificate tags.
 */
@UtilityClass
public class TagValidator {
    private static final String ONLY_LETTERS = "^[a-zA-Z]*$";

    /**
     * Validates a tag to ensure that its parameters are valid.
     *
     * @param tag The tag to validate.
     * @throws ValidationException if any of the tag parameters are not valid.
     */
    public void validate(TagDTO tag) {
        validateName(tag.getName());
    }

    private void validateName(String name) {
        CustomValidator.notNull(TagField.NAME, name);
        CustomValidator.notBlank(TagField.NAME, name);
        CustomValidator.notTooShortOrLong(TagField.NAME, name, 3, 30);

        if (!name.matches(ONLY_LETTERS)) {
            throw new ValidationException(TagField.NAME.getName() + " must include only letters");
        }
    }
}
