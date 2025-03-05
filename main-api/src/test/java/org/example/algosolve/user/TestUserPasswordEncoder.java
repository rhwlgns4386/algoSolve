package org.example.algosolve.user;

import org.example.algosolve.user.domain.UserPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class TestUserPasswordEncoder implements UserPasswordEncoder {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String input, String password) {
        return passwordEncoder.matches(input, password);
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
