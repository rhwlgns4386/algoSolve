package org.example.algosolve.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.user.token.TokenProvider;
import org.example.algosolve.user.token.TokenType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class JwtAuthenticationFilter extends JwtExceptionHandleFilter {
    private final TokenProvider tokenProvider;
    private final TokenType tokenType;

    public JwtAuthenticationFilter(TokenProvider provider, TokenType tokenType, ObjectMapper objectMapper) {
        super(objectMapper);
        this.tokenProvider=provider;
        this.tokenType = tokenType;
    }

    @Override
    protected void authenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenProvider.extractTokenFromHeader(request);
        validToken(token);

        SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
    }

    private void validToken(String token) {
        if(token == null){
            throw new BadCredentialsException("토큰이 존재하지 않습니다.");
        }

        if(!tokenProvider.checkType(tokenType, token)){
            throw new UnsupportedJwtException("잘못된 토큰이 입력되었습니다.");
        }
    }
}
