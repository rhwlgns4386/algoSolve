package org.example.algosolve.user.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.example.algosolve.user.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HS256JwtBuilderProvider {

    private final Key secret;

    public HS256JwtBuilderProvider(@Value("${jwt.key}") String secret) {
        this.secret = toKey(secret);
    }

    JwtBuilder builder(){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .signWith(secret, SignatureAlgorithm.HS256);
    }

    private SecretKey toKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims claims(String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token).getBody();
    }
}
