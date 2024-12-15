package org.example.algosolve.user.token;

import static org.example.algosolve.user.token.TokenHeaderExtractor.extract;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Collections;

import org.example.algosolve.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final TokenEncoder tokenEncoder;
    private final TokenDecoder tokenDecoder;

    @Autowired
    public TokenProvider(HS256JwtBuilderProvider jwtBuilderProvider,
                         @Value("${auth.access-token-expire-minute}") int accessTokenExpireMinute,
                         @Value("${auth.refresh-token-expire-minute}") int refreshTokenExpireMinute) {
        this(new TokenEncoder(jwtBuilderProvider, accessTokenExpireMinute, refreshTokenExpireMinute),new TokenDecoder(jwtBuilderProvider));
    }

    public TokenProvider(TokenEncoder tokenEncoder,TokenDecoder tokenDecoder){
        this.tokenEncoder=tokenEncoder;
        this.tokenDecoder=tokenDecoder;
    }

    public String createAccessToken(LocalDateTime now, String userId) {
        return tokenEncoder.accessToken(now, userId);
    }

    public Cookie creatRefreshTokenCookie(LocalDateTime now, String userId) {
        Cookie refreshToken = new Cookie("refreshToken", tokenEncoder.refresh(now, userId));
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(tokenEncoder.refreshTokenExpireMinute() * 60);
        return refreshToken;
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        return extract(request);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = tokenDecoder.decode(token);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
    }
}
