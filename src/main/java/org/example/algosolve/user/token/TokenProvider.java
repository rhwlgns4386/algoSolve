package org.example.algosolve.user.token;

import static org.example.algosolve.user.token.TokenHeaderExtractor.extract;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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

    public String extractUserId(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
    }

    private Claims getClaims(String token) {
        try{
            return tokenDecoder.decode(token);
        }catch (ExpiredJwtException e){
            throw new BadCredentialsException("기간이 만료되었습니다.");
        }catch (SignatureException | MalformedJwtException e){
            throw new BadCredentialsException("토큰이 손상되었습니다.");
        }
    }
}
