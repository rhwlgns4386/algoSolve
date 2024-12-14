package org.example.algosolve.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.dto.SignupDto;
import org.example.algosolve.user.dto.TokenDto;
import org.example.algosolve.user.service.UserService;
import org.example.algosolve.user.service.UserData;
import org.example.algosolve.user.token.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signup")
    public void signup(@RequestBody @Validated SignupDto signupDto) {
        userService.signup(signupDto);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody IdPasswordDto idPasswordDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(idPasswordDto.getUserId(), idPasswordDto.getPassword());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();
        authenticationManager.authenticate(authenticationToken);

        LocalDateTime now = LocalDateTime.now();
        response.addCookie(tokenProvider.creatRefreshTokenCookie(now, idPasswordDto.getUserId()));
        return new TokenDto(tokenProvider.createAccessToken(now, idPasswordDto.getUserId()));
    }

}
