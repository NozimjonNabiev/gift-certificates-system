package com.epam.esm.validator;

import com.epam.esm.entity.User;
import com.epam.esm.util.enums.field.UserField;
import lombok.experimental.UtilityClass;

/**
 * {@code UserValidator} is a utility class that provides validation methods
 * for the {@link User} entity.
 *
 * <p>The class is annotated with {@link UtilityClass}, indicating that it is
 * a utility class and contains only static methods.
 *
 * @see UtilityClass
 */
@UtilityClass
public class UserValidator {

    /**
     * Validates the user entity.
     *
     * @param user The user entity to validate.
     */
    public void validate(User user) {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
    }

    /**
     * Validates the username.
     *
     * @param username The username to validate.
     */
    private void validateUsername(String username) {
        CustomValidator.notNull(UserField.USERNAME, username);
        CustomValidator.notBlank(UserField.USERNAME, username);
        CustomValidator.notTooShortOrLong(UserField.USERNAME, username, 3, 30);
        CustomValidator.onlyLetters(UserField.USERNAME, username);
    }

    /**
     * Validates the password.
     *
     * @param password The password to validate.
     */
    private void validatePassword(String password) {
        CustomValidator.notNull(UserField.PASSWORD, password);
        CustomValidator.notBlank(UserField.PASSWORD, password);
        CustomValidator.notTooShortOrLong(UserField.PASSWORD, password, 8, 20);
        CustomValidator.atLeastOneUppercaseLetterLowercaseLetterNumberAndSpecialCharacter(UserField.PASSWORD, password);
    }
}
