package org.example.algosolve.user.service;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserPasswordEncoder;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.dto.SignupDto;
import org.example.algosolve.user.exeption.DuplicateUserIdException;
import org.example.algosolve.user.token.TokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserPasswordEncoder passwordEncoder;

    public void signup(SignupDto signupDto) {
        if (userRepository.existsByUserId(signupDto.getUserId())) {
            throw new DuplicateUserIdException();
        }
        User user = signupDto.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    private User findByUserid(String userId) {
        return userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
    }
}
