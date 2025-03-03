package org.example.algosolve.user.service;

import org.example.algosolve.user.TestUser;
import org.example.algosolve.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    private final AuthService authService = new AuthService(mockUserRepository(),null,null);


    @Test
    void 아이디_존재_있음(){
        assertThat(authService.containId(TestUser.USER.getUserId())).isTrue();
    }

    @Test
    void 아이디_존재_없음(){
        assertThat(authService.containId(TestUser.USER.getUserId() + "4")).isFalse();
    }

    private static UserRepository mockUserRepository(){
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUserId(eq(TestUser.USER.getUserId()))).thenReturn(true);
        when(userRepository.existsByUserId(AdditionalMatchers.not(eq(TestUser.USER.getUserId())))).thenReturn(false);
        return userRepository;
    }
}
