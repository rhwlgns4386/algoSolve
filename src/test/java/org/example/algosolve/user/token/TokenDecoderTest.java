package org.example.algosolve.user.token;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.algosolve.user.util.DateUtil.toDate;

public class TokenDecoderTest {

    private static final HS256JwtBuilderProvider jwtProvider= new HS256JwtBuilderProvider("testtesttesttesttesttesttesttesttesttesttest");

    @Test
    void 토큰을_디코딩한다(){
        String id = "test";
        int timeInterval=20;
        String token = jwtProvider.builder()
                .setSubject(id)
                .setIssuedAt(toDate(LocalDateTime.now()))
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(timeInterval)))
                .compact();

        TokenDecoder tokenDecoder=new TokenDecoder(jwtProvider);
        Claims claims=tokenDecoder.decode(token);

        assertThat(claims.getSubject()).isEqualTo(id);
        assertThat(calcTimeInterval(claims)).isEqualTo(timeInterval);
    }

    private static long calcTimeInterval(Claims claims) {
        return (claims.getExpiration().getTime() - claims.getIssuedAt().getTime()) / 60000;
    }
}
