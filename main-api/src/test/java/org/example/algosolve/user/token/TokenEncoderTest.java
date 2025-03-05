package org.example.algosolve.user.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenEncoderTest {

    @Test
    void accessToken생성(){
        String key="testtesttesttesttesttesttesttesttesttesttest";
        int accessTokenExpireTime = 30;
        TokenEncoder tokenEncoder=new TokenEncoder(new HS256JwtBuilderProvider(key),accessTokenExpireTime,100);

        LocalDateTime now = LocalDateTime.now();
        String id = "testData";
        String token=tokenEncoder.accessToken(now,id);

        Claims claims = toClaims(key, token);
        assertThat(claims.getSubject()).isEqualTo(id);
        assertThat(calcTimeInterval(claims)).isEqualTo(accessTokenExpireTime);
    }

    @Test
    void refreshToken생성(){
        String key="testtesttesttesttesttesttesttesttesttesttest";
        int refreshTokenExpireTime = 100;
        TokenEncoder tokenEncoder=new TokenEncoder(new HS256JwtBuilderProvider(key),30,refreshTokenExpireTime);

        LocalDateTime now = LocalDateTime.now();
        String id = "testData";
        String token=tokenEncoder.refresh(now,id);

        Claims claims = toClaims(key, token);
        assertThat(claims.getSubject()).isEqualTo(id);
        assertThat(calcTimeInterval(claims)).isEqualTo(refreshTokenExpireTime);
    }

    private static Claims toClaims(String key, String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token).getBody();
    }

    private static long calcTimeInterval(Claims claims) {
        return (claims.getExpiration().getTime() - claims.getIssuedAt().getTime()) / 60000;
    }
}
