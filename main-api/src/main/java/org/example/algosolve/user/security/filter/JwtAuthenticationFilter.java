package org.example.algosolve.user.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.global.exception.response.ErrorCode;
import org.example.algosolve.user.security.filter.JwtAuthenticationProvider;
import org.example.algosolve.user.security.filter.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtAuthenticationFilter(ObjectMapper objectMapper, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.objectMapper = objectMapper;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Result result = jwtAuthenticationProvider.authenticationToken(request, response);
        if (result.hasError()) {
            log.error("",result.getException());
            ErrorResponseWriter errorResponseWriter = new ErrorResponseWriter(response);
            errorResponseWriter.writeError(result.getErrorCode(), objectMapper.writeValueAsString(result.getErrorResponse()));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(result.getAuthentication());
        filterChain.doFilter(request, response);
    }

    private class ErrorResponseWriter {

        private final HttpServletResponse response;

        public ErrorResponseWriter(HttpServletResponse response) {
            this.response = response;
        }

        private void writeError(ErrorCode errorCode, String message) throws IOException {
            writeError(errorCode.getHttpStatus().value(), message);
        }

        private void writeError(int status,  String message) throws IOException {
            response.setStatus(status);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(message);
        }
    }
}
