package org.example.algosolve.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.algosolve.user.token.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    public JwtAuthenticationFilter(TokenProvider provider) {
        this.tokenProvider=provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 토큰을 추출
        String token = tokenProvider.extractTokenFromHeader(request);

        if (token != null) {
            // 유효성 검증 이후 토큰을 context에 저장
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
