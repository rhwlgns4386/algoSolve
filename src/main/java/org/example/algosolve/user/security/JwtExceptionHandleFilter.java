package org.example.algosolve.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.global.exception.response.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public abstract class JwtExceptionHandleFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public JwtExceptionHandleFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Result result = process(request, response);
        if (result.hasError()) {
            log.error("",result.getException());
            ResponseWriter responseWriter = new ResponseWriter(response);
            responseWriter.writeError(result.getErrorCode(), result.getErrorResponse());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Result process(HttpServletRequest request, HttpServletResponse response){
        try {
            authenticationToken(request, response);
            return new Result();
        } catch (AuthenticationException authenticationException) {
            ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
            return new Result(errorCode, authenticationException.getMessage(), request.getRequestURI(), authenticationException);
        } catch (ExpiredJwtException e) {
            ErrorCode errorCode = ErrorCode.EXPIRED_TOKEN;
            return new Result(errorCode, "만료된 토큰입니다.", request.getRequestURI(), e);
        } catch (UnsupportedJwtException | SignatureException | MalformedJwtException e) {
            ErrorCode code = ErrorCode.INVALID_TOKEN;
            return new Result(code, "토큰이 손상 되었거나 알 수 없습니다", request.getRequestURI(), e);
        } catch (IllegalArgumentException e) {
            ErrorCode code = ErrorCode.BAD_REQUEST;
            return new Result(code, e.getMessage(), request.getRequestURI(),e);
        }
    }

    protected abstract void authenticationToken(HttpServletRequest request, HttpServletResponse response);

    @Getter
    @NoArgsConstructor
    private static class Result {
        private ErrorCode errorCode;
        private ErrorResponse errorResponse;
        private Exception exception;

        private Result(ErrorCode errorCode, String message, String url, Exception exception) {
            this.errorCode = errorCode;
            errorResponse = ErrorResponseFactory.createErrorResponse(errorCode, message, url);
            this.exception = exception;
        }

        private boolean hasError() {
            return exception != null;
        }
    }

    private static class ErrorResponseFactory {

        private static ErrorResponse createErrorResponse(ErrorCode code, String message, String url) {
            return createErrorResponse(code.getCode(), message, url);
        }

        private static ErrorResponse createErrorResponse(String code, String message, String url) {
            return new ErrorResponse(code, message, url);
        }
    }

    private class ResponseWriter {

        private final HttpServletResponse response;

        public ResponseWriter(HttpServletResponse response) {
            this.response = response;
        }

        private void writeError(ErrorCode errorCode, ErrorResponse errorResponse) throws IOException {
            writeError(errorCode.getHttpStatus().value(), errorResponse);
        }

        private void writeError(int status, ErrorResponse errorResponse) throws IOException {
            response.setStatus(status);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}
