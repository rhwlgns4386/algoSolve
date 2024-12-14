package org.example.algosolve.user.domain;

public interface UserPasswordEncoder {
    boolean matches(String input, String password);

    String encode(String password);
}
