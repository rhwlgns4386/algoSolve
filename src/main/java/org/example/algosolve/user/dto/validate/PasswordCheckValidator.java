package org.example.algosolve.user.dto.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.algosolve.user.dto.SignupDto.Credentials;

public class PasswordCheckValidator implements ConstraintValidator<PasswordCheck, Credentials> {
    private static final String NODE_NAME = "passwordCheck";
    private String message;

    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Credentials credentials, ConstraintValidatorContext context) {
        if (credentials.passwordMatched()) {
            return true;
        }
        addConstraintViolation(context, message, NODE_NAME);
        return false;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String errorMessage, String firstNode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addPropertyNode(firstNode)
                .addConstraintViolation();
    }
}
