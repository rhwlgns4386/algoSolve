package org.example.algosolve.user.token;

import static org.example.algosolve.user.util.DateUtil.toDate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.SignatureException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.example.algosolve.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.example.algosolve.user.service.UserData;

@Component
public class TokenProvider {

    private final HS256JwtBuilderProvider jwtBuilderProvider;
    private final TokenExpireMinute accessTokenExpireMinute;
    private final TokenExpireMinute refreshTokenExpireMinute;

    public TokenProvider(HS256JwtBuilderProvider jwtBuilderProvider,
                         @Value("${auth.access-token-expire-minute}") int accessTokenExpireMinute,
                         @Value("${auth.refresh-token-expire-minute}") int refreshTokenExpireMinute) {
        this.jwtBuilderProvider = jwtBuilderProvider;
        this.accessTokenExpireMinute = new TokenExpireMinute(accessTokenExpireMinute);
        this.refreshTokenExpireMinute = new TokenExpireMinute(refreshTokenExpireMinute);
    }

    public String createAccessToken(LocalDateTime now, String userId) {
        return jwtBuilderProvider.builder()
                .setSubject(userId)
                .setIssuedAt(toDate(now))
                .setExpiration(accessTokenExpireMinute.calculateExpirationDate(now))
                .compact();
    }

    public Cookie creatRefreshTokenCookie(LocalDateTime now, String userId) {
        Cookie refreshToken = new Cookie("refreshToken", createRefreshToken(now, userId));
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(refreshTokenExpireMinute.toInt() * 60);
        return refreshToken;
    }

    public String createRefreshToken(LocalDateTime now, String userId) {
        return jwtBuilderProvider.builder()
                .setSubject(userId)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(toDate(now))
                .setExpiration(refreshTokenExpireMinute.calculateExpirationDate(now))
                .compact();
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String data = request.getHeader("Authorization");
        if(data==null){
            throw new BadCredentialsException("Authorization에 토큰이 존재 하지 않습니다");
        }

        return extractToken(data);
    }

    private String extractToken(String data) {
        if(data.startsWith("Bearer ")){
            return data.substring(7);
        }
        throw new BadCredentialsException("Bearer로 시작하지 않습니다");
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtBuilderProvider.claims(token);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(),null, Collections.emptyList());
    }
}
