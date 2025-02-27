package org.example.algosolve.user.security;

import org.example.algosolve.user.token.TokenProvider;
import org.example.algosolve.user.token.TokenType;

public class TokenTypeChecker {

    private TokenType tokenType;
    private TokenProvider tokenProvider;

    public TokenTypeChecker(TokenType targetType, TokenProvider tokenProvider){
        this.tokenType = targetType;
        this.tokenProvider = tokenProvider;
    }

    public boolean check(String token){
        TokenType type = tokenProvider.extractTokenType(token);
        return type == tokenType;
    }
}
