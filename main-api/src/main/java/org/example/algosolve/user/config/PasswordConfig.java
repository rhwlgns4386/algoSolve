package org.example.algosolve.user.config;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.UserPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class PasswordConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserPasswordEncoder userPasswordEncoder(){
        return new UserPasswordEncoder() {
            @Override
            public boolean matches(String input, String old) {
                return passwordEncoder.matches(input,old);
            }

            @Override
            public String encode(String password) {
                return passwordEncoder.encode(password);
            }
        };
    }
}
