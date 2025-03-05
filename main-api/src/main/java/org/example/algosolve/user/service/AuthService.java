package org.example.algosolve.user.service;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserPasswordEncoder;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.dto.SignupDto;
import org.example.algosolve.user.exception.DuplicateUserIdException;
import org.example.algosolve.user.exception.InvalidRefreshTokenException;
import org.example.algosolve.user.exception.PasswordInValidException;
import org.example.algosolve.user.exception.UserNotFoundException;
import org.example.algosolve.user.token.TokenEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserPasswordEncoder passwordEncoder;
    private final TokenEncoder tokenEncoder;

    public LoginInfo login(LocalDateTime now, String id, String password) throws UserNotFoundException, PasswordInValidException {
        User user = userRepository.findUserByUserId(id).orElseThrow(UserNotFoundException::new);
        if(!user.matchPassword(password, passwordEncoder)){
            throw new PasswordInValidException();
        }
        String refreshToken = tokenEncoder.refresh(now, id);
        user.updateRefreshToken(refreshToken);
        return new LoginInfo(refreshToken,tokenEncoder.accessToken(now,id),user.getNickName());
    }

    public void signup(SignupDto signupDto) throws DuplicateUserIdException {
        if (userRepository.existsByUserId(signupDto.getUserId())) {
            throw new DuplicateUserIdException();
        }
        User user = signupDto.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    public String issueAccessToken(LocalDateTime now, String id, String refreshToken) throws InvalidRefreshTokenException {
        if(!isUserToken(id,refreshToken)){
            throw new InvalidRefreshTokenException();
        }
        return tokenEncoder.accessToken(now,id);
    }

    private boolean isUserToken(String id, String refreshToken){
        Optional<User> user = userRepository.findUserByUserIdAndRefreshToken(id,refreshToken);
        return user.isPresent();
    }

    public boolean containId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
