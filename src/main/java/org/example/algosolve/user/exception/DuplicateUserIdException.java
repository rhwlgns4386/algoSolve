package org.example.algosolve.user.exception;

import org.springframework.security.core.AuthenticationException;

public class DuplicateUserIdException extends AuthenticationException {
    private static final String MESSAGE = "아이디가 중복 됩니다.";

    public DuplicateUserIdException() {
        super(MESSAGE);
    }
}
