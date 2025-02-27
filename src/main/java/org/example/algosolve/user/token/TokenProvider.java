package org.example.algosolve.user.token;

import static org.example.algosolve.user.controller.TokenHeaderExtractor.extract;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final TokenEncoder tokenEncoder;
    private final TokenDecoder tokenDecoder;

    public String createAccessToken(LocalDateTime now, String userId) {
        return tokenEncoder.accessToken(now, userId);
    }

    public Cookie creatRefreshTokenCookie(String token) {
        Cookie refreshToken = new Cookie("refreshToken", token);
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(tokenEncoder.refreshTokenExpireMinute() * 60);
        return refreshToken;
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        return extract(request);
    }

    public boolean checkType(TokenType targetType,String token){
        TokenType tokenType = extractTokenType(token);
        return targetType == tokenType;
    }

    public String extractUserId(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
    }

    public TokenType extractTokenType(String token) {
        Claims claims = getClaims(token);
        String tokenType = (String)claims.get("token_type");
        return TokenType.from(tokenType);
    }

    private Claims getClaims(String token) {
        return tokenDecoder.decode(token);
    }
}
