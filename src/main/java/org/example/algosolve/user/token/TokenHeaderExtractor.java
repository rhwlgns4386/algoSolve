package org.example.algosolve.user.token;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;

public class TokenHeaderExtractor {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private TokenHeaderExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        return extractToken(headerData(request));
    }

    private static String headerData(HttpServletRequest request) {
        String data = request.getHeader(TOKEN_HEADER);
        if(data!=null){
            return data;
        }
        throw new BadCredentialsException("Authorization에 토큰이 존재 하지 않습니다");
    }

    private static String extractToken(String data) {
        if(data.startsWith(TOKEN_PREFIX)){
            return data.substring(7);
        }
        throw new BadCredentialsException("Bearer로 시작하지 않습니다");
    }
}
