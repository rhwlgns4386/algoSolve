package org.example.algosolve.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"4000"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"4010"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "4011"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "4012")
    ;

    private final HttpStatus httpStatus;
    private final String code;

    ErrorCode(HttpStatus httpStatus, String code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }
}
