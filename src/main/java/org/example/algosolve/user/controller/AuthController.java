package org.example.algosolve.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.dto.SignupDto;
import org.example.algosolve.user.dto.TokenDto;
import org.example.algosolve.user.service.AuthService;
import org.example.algosolve.user.service.TokenInfo;
import org.example.algosolve.user.token.TokenProvider;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @GetMapping("/issued_access_token")
    public TokenDto issuedAccessToken(HttpServletRequest httpServletRequest) {
        String refreshToken = tokenProvider.extractTokenFromHeader(httpServletRequest);
        String id = tokenProvider.extractUserId(refreshToken);
        return new TokenDto(authService.issueAccessToken(LocalDateTime.now(), id, refreshToken));
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Validated SignupDto signupDto) {
        authService.signup(signupDto);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody IdPasswordDto idPasswordDto, HttpServletResponse response) {
        TokenInfo tokenInfo = authService.login(LocalDateTime.now(), idPasswordDto.getUserId(), idPasswordDto.getPassword());
        response.addCookie(tokenProvider.creatRefreshTokenCookie(tokenInfo.getRefreshToken()));
        return new TokenDto(tokenInfo.getAccessToken());
    }

}
