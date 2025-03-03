package org.example.algosolve.user.security.filter;

import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.security.filter.JwtAuthenticationProvider;
import org.example.algosolve.user.security.filter.Result;
import org.example.algosolve.user.token.TokenProvider;
import org.example.algosolve.user.token.TokenType;
import org.springframework.security.authentication.BadCredentialsException;

@RequiredArgsConstructor
public class JwtAuthenticationProviderImpl implements JwtAuthenticationProvider {

    private final TokenProvider tokenProvider;
    private final TokenType tokenType;


    @Override
    public Result authenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenProvider.extractTokenFromHeader(request);
        validToken(token);
        return new Result(tokenProvider.getAuthentication(token));
    }


    private void validToken(String token) {
        if(token == null){
            throw new BadCredentialsException("토큰이 존재하지 않습니다.");
        }

        if(!tokenProvider.checkType(tokenType, token)){
            throw new UnsupportedJwtException("잘못된 토큰이 입력되었습니다.");
        }
    }
}
