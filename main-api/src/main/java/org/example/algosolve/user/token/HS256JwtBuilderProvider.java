package org.example.algosolve.user.token;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class HS256JwtBuilderProvider {

    private final Key secret;

    @Autowired
    public HS256JwtBuilderProvider(@Value("${jwt.key}") String secret) {
        this(toKey(secret));
    }

    public HS256JwtBuilderProvider(Key key){
        this.secret=key;
    }

    private static SecretKey toKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    JwtBuilder builder(){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .signWith(secret, SignatureAlgorithm.HS256);
    }

    JwtParser parser(){
        return  Jwts.parserBuilder()
                .setSigningKey(secret)
                .build();
    }
}
