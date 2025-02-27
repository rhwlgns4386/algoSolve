package org.example.algosolve.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
        try {
            authenticationToken(request,response);
        }catch (AuthenticationException authenticationException) {
            log.info("{}",authenticationException);
            writeError(response,HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage(),request.getRequestURI());
            return;
        } catch (IllegalArgumentException illegalArgumentException) {
            log.info("{}",illegalArgumentException);
            writeError(response,HttpServletResponse.SC_BAD_REQUEST, illegalArgumentException.getMessage(),request.getRequestURI());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void writeError(HttpServletResponse response,int status,String message,String url) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(status, message, url);

        // 응답 설정
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    protected abstract void authenticationToken(HttpServletRequest request, HttpServletResponse response);
}
