package org.example.algosolve.user.controller;

import jakarta.servlet.http.HttpServletRequest;

public class TokenHeaderExtractor {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private TokenHeaderExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        return extractToken(headerData(request));
    }

    private static String headerData(HttpServletRequest request) {
        if(request==null) return null;
        String data = request.getHeader(TOKEN_HEADER);
        if(data!=null){
            return data;
        }
        return null;
    }

    private static String extractToken(String data) {
        if(data==null) return null;
        if(data.startsWith(TOKEN_PREFIX)){
            return data.substring(7);
        }
        return null;
    }
}
