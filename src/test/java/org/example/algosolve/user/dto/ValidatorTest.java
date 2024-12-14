package org.example.algosolve.user.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidatorTest {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected static <T> boolean hasError(T dto) {
        return !validator.validate(dto).isEmpty();
    }
}
