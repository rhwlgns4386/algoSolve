package org.example.algosolve.user.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;

public class TokenDecoder {
    private final HS256JwtBuilderProvider jwtBuilderProvider;
    public TokenDecoder(HS256JwtBuilderProvider jwtProvider) {
        this.jwtBuilderProvider=jwtProvider;
    }

    public Claims decode(String token) {
        JwtParser parser = jwtBuilderProvider.parser();
        return parser.parseClaimsJws(token).getBody();
    }
}
