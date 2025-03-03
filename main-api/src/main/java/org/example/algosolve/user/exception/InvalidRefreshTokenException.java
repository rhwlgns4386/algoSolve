package org.example.algosolve.user.exception;

public class InvalidRefreshTokenException extends Exception {

    private static final String message = "토큰 정보가 일치하지 않습니다.";

    public InvalidRefreshTokenException() {
        super(message);
    }
}
