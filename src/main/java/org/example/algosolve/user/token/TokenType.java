package org.example.algosolve.user.token;

import java.util.Arrays;

public enum TokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN;

    public static TokenType from(String tokenInput) {
        return Arrays.stream(values()).filter(tokenType -> tokenType.name().equalsIgnoreCase(tokenInput)).findAny().orElseThrow(() -> new IllegalArgumentException("토큰 타입을 찾을 수 없습니다."));
    }
}
