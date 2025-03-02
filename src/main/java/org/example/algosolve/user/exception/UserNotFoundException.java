package org.example.algosolve.user.exception;

public class UserNotFoundException extends Exception {

    private static final String message = "사용자를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(message);
    }
}
