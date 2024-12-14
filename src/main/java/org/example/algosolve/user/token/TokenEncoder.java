package org.example.algosolve.user.token;

import java.time.LocalDateTime;

import static org.example.algosolve.user.util.DateUtil.toDate;

public class TokenEncoder {
    private final HS256JwtBuilderProvider hs256JwtBuilderProvider;
    private final TokenExpireMinute accessTokenExpireMinute;
    private final TokenExpireMinute refreshTokenExpireMinute;
    public TokenEncoder(HS256JwtBuilderProvider jwtBuilderProvider,
                        int accessTokenExpireMinute,
                        int refreshTokenExpireMinute) {
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
