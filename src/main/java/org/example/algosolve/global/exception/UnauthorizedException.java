package org.example.algosolve.global.exception;

import org.example.algosolve.global.exception.response.ErrorCode;

public class UnauthorizedException extends ResponseException {

    private static final ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UnauthorizedException(ErrorCode errorCode, String message, Throwable e) {
        super(errorCode, message, e);
    }

    public UnauthorizedException(String message) {
        super(errorCode, message);
    }

    public UnauthorizedException(String message, Throwable e) {
        super(errorCode, message, e);
    }
}
