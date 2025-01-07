package org.example.algosolve.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandleFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public ExceptionHandleFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authenticationException) {
            authenticationException.printStackTrace();
            writeError(response,HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage(),request.getRequestURI());
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            writeError(response,HttpServletResponse.SC_BAD_REQUEST, illegalArgumentException.getMessage(),request.getRequestURI());
        }


    }

    private void writeError(HttpServletResponse response,int status,String message,String url) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(status, message, url);

        // 응답 설정
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
