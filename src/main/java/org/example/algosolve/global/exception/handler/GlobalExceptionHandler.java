package org.example.algosolve.global.exception.handler;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.global.exception.ResponseException;
import org.example.algosolve.global.exception.response.ErrorCode;
import org.example.algosolve.global.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(AccessDeniedException.class)
//    ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
//        ErrorResponse response = ErrorResponse.of(HttpStatus.CONFLICT.value(), accessDeniedException.getMessage());
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException accessDeniedException) {
//        ErrorResponse response = ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), accessDeniedException.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }
//
//
//    @ExceptionHandler(ConflictException.class)
//    ResponseEntity<ErrorResponse> handleConflictException(ConflictException conflictException) {
//        ErrorResponse response = ErrorResponse.of(HttpStatus.CONFLICT.value(), conflictException.getMessage());
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//    }

    @ExceptionHandler(ResponseException.class)
    ResponseEntity<ErrorResponse> handleConflictException(ResponseException responseException) {
        ErrorResponse response = ErrorResponse.of(responseException.errorCode(), responseException.getMessage());
        return ResponseEntity.status(responseException.httpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_REQUEST.getCode(), fieldErrors);
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatus()).body(errorResponse);
    }
}
