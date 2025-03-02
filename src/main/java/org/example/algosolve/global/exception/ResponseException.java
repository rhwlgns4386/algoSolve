package org.example.algosolve.global.exception;

import org.example.algosolve.global.exception.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {

    private final ErrorCode errorCode;


    public ResponseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResponseException(ErrorCode errorCode, String message, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public HttpStatus httpStatus() {
        return errorCode.getHttpStatus();
    }

    public String errorCode() {
        return errorCode.getCode();
    }
}
