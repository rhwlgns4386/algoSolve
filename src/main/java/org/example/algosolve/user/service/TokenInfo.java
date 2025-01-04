package org.example.algosolve.user.service;

import lombok.Data;

@Data
public class TokenInfo {
    private final String refreshToken;
    private final String accessToken;
}
