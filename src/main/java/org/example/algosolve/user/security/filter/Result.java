package org.example.algosolve.user.security.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.algosolve.global.exception.response.ErrorCode;
import org.springframework.security.core.Authentication;

@Getter
@NoArgsConstructor
public class Result {
    private ErrorCode errorCode;
    private ErrorResponse errorResponse;
    private Exception exception;
    @Getter
    private Authentication authentication;

    Result(ErrorCode errorCode, String message, String url, Exception exception) {
        this.errorCode = errorCode;
        errorResponse = ErrorResponseFactory.createErrorResponse(errorCode, message, url);
        this.exception = exception;
    }

    public Result(Authentication authentication) {
        this.authentication = authentication;
    }

    boolean hasError() {
        return exception != null;
    }

    private static class ErrorResponseFactory {

        private static ErrorResponse createErrorResponse(ErrorCode code, String message, String url) {
            return createErrorResponse(code.getCode(), message, url);
        }

        private static ErrorResponse createErrorResponse(String code, String message, String url) {
            return new ErrorResponse(code, message, url);
        }
    }
}
