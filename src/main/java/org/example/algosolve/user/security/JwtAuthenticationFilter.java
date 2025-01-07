package org.example.algosolve.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.algosolve.user.token.TokenProvider;
import org.example.algosolve.user.token.TokenType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final TokenTypeChecker tokenTypeChecker;
    public JwtAuthenticationFilter(TokenProvider provider, TokenType tokenType) {
        this.tokenProvider=provider;
        this.tokenTypeChecker = new TokenTypeChecker(tokenType,tokenProvider);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = tokenProvider.extractTokenFromHeader(request);
        if(token == null){
            throw new BadCredentialsException("토큰이 존재하지 않습니다.");
        }
        if(!tokenTypeChecker.check(token)){
            throw new IllegalArgumentException("잘못된 토큰이 입력되었습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
