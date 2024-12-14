package org.example.algosolve.user.dto;

import org.example.algosolve.user.dto.SignupDto;

public class TestSignupDtoFactory {
    public static SignupDto signupDto(String userId, String password, String passwordCheck, int level) {
        return new SignupDto(userId, password, passwordCheck, level, "");
    }
}
