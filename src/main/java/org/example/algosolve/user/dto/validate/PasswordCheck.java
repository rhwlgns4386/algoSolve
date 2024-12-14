package org.example.algosolve.user.dto.validate;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordCheckValidator.class)
public @interface PasswordCheck {

    String message() default "비밀번호가 인증번호와 일치하지 않습니다";

    Class[] groups() default {};

    Class[] payload() default {};
}
