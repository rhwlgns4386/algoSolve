package org.example.algosolve.global.exception;

import org.example.algosolve.global.exception.UnauthorizedException;
import org.example.algosolve.global.exception.response.ErrorCode;

public class InvalidTokenException extends UnauthorizedException {

    private static final ErrorCode errorCode = ErrorCode.INVALID_TOKEN;
    private static final String message = "인증 정보가 일치하지 않습니다.";

    public InvalidTokenException() {
        super(errorCode, message);
    }

    public InvalidTokenException(Throwable e) {
        super(errorCode, message, e);
    }
}
