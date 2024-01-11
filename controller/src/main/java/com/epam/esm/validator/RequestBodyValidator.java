package com.epam.esm.validator;

import com.epam.esm.exception.InvalidRequestBodyException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Utility class for validating request bodies using Spring's BindingResult.
 */
@Slf4j
@UtilityClass
public class RequestBodyValidator {

    /**
     * Validates the provided BindingResult for request body errors.
     *
     * @param bindingResult The BindingResult object containing potential errors.
     * @throws InvalidRequestBodyException if there are validation errors in the request body.
     */
    public static void validate(BindingResult bindingResult) throws InvalidRequestBodyException {
        log.info("Validating request body...");
        if (bindingResult.hasErrors()) {
            List<String> violations = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            log.error("Request body validation failed with {} violations", violations.size());
            throw new InvalidRequestBodyException(violations.toArray(new String[0]));
        }
        log.info("Request body validation succeeded");
    }
}
