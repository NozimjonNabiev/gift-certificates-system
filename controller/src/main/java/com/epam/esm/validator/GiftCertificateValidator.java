package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.util.enums.GiftCertificateField;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * Utility class for validating gift certificates.
 */
@UtilityClass
public class GiftCertificateValidator {
    /**
     * Validates a gift certificate to ensure that its parameters are valid.
     *
     * @param giftCertificate The gift certificate to validate.
     * @throws ValidationException if any of the gift certificate parameters are not valid.
     */
    public void validate(GiftCertificateDTO giftCertificate) {
        validateName(giftCertificate.getName());
        validateDescription(giftCertificate.getDescription());
        validatePrice(giftCertificate.getPrice());
        validateDuration(giftCertificate.getDuration());
        validateTags(giftCertificate.getTags());
    }

    /**
     * Validates a gift certificate name to ensure that it's not null, empty or blank,
     * includes the required number of characters, and does not include special characters.
     *
     * @param name String name to validate.
     * @throws ValidationException if name is not valid.
     */
    public void validateName(String name) {
        CustomValidator.notNull(GiftCertificateField.NAME, name);
        CustomValidator.notBlank(GiftCertificateField.NAME, name);
        CustomValidator.notTooShortOrLong(GiftCertificateField.NAME, name, 4, 40);
        CustomValidator.onlyLettersAndSpaces(GiftCertificateField.NAME, name);
    }

    void validateDescription(String description) {
        CustomValidator.notNull(GiftCertificateField.DESCRIPTION, description);
        CustomValidator.notBlank(GiftCertificateField.DESCRIPTION, description);
        CustomValidator.notTooShortOrLong(GiftCertificateField.DESCRIPTION, description, 4, 200);
        CustomValidator.onlyLettersAndSpaces(GiftCertificateField.DESCRIPTION, description);
    }

    public void validatePrice(Double price) {
        CustomValidator.notNull(GiftCertificateField.PRICE, price);
        CustomValidator.notNegative(GiftCertificateField.PRICE, price);
        CustomValidator.notTooLowOrHigh(GiftCertificateField.PRICE, price, 10, 100);
    }

    void validateDuration(Integer duration) {
        CustomValidator.notNull(GiftCertificateField.DURATION, duration);
        CustomValidator.notNegative(GiftCertificateField.DURATION, Double.valueOf(duration));
        CustomValidator.notTooLowOrHigh(GiftCertificateField.DURATION, Double.valueOf(duration), 10, 100);
    }

    void validateTags(Set<TagDTO> tags) {
        CustomValidator.notNull(GiftCertificateField.TAGS, tags);
        tags.forEach(TagValidator::validate);
    }
}
