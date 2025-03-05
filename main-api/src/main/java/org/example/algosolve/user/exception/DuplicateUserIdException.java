package org.example.algosolve.user.exception;

public class DuplicateUserIdException extends Exception {
    private static final String MESSAGE = "아이디가 중복 됩니다.";

    public DuplicateUserIdException() {
        super(MESSAGE);
    }
}
