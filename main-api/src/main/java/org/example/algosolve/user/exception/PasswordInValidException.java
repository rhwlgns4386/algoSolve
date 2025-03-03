package org.example.algosolve.user.exception;

public class PasswordInValidException extends Exception {

    private static final String message = "패스워드가 일치하지 않습니다.";

    public PasswordInValidException() {
        super(message);
    }
}
