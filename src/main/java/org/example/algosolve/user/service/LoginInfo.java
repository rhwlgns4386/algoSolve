package org.example.algosolve.user.service;

import lombok.Data;

@Data
public class LoginInfo {
    private final String refreshToken;
    private final String accessToken;
    private final String nickName;
}
