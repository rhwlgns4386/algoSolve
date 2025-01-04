package org.example.algosolve.user.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.example.algosolve.user.util.DateUtil.toDate;

@Component
public class TokenEncoder {
    private final HS256JwtBuilderProvider hs256JwtBuilderProvider;
    private final TokenExpireMinute accessTokenExpireMinute;
    private final TokenExpireMinute refreshTokenExpireMinute;
    public TokenEncoder(HS256JwtBuilderProvider jwtBuilderProvider,
                        @Value("${auth.access-token-expire-minute}") int accessTokenExpireMinute,
                        @Value("${auth.refresh-token-expire-minute}") int refreshTokenExpireMinute) {
        this.hs256JwtBuilderProvider=jwtBuilderProvider;
        this.accessTokenExpireMinute=new TokenExpireMinute(accessTokenExpireMinute);
        this.refreshTokenExpireMinute=new TokenExpireMinute(refreshTokenExpireMinute);
    }

    public String accessToken(LocalDateTime now, String id) {
        return hs256JwtBuilderProvider.builder()
                .setSubject(id)
                .setIssuedAt(toDate(now))
                .setExpiration(accessTokenExpireMinute.calculateExpirationDate(now))
                .compact();
    }

    public String refresh(LocalDateTime now, String id) {
        return hs256JwtBuilderProvider.builder()
                .setSubject(id)
                .setIssuedAt(toDate(now))
                .setExpiration(refreshTokenExpireMinute.calculateExpirationDate(now))
                .compact();
    }

    public int refreshTokenExpireMinute(){
        return refreshTokenExpireMinute.toInt();
    }
}
