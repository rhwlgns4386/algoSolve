package org.example.algosolve.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.dto.IdDto;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.dto.SignupDto;
import org.example.algosolve.user.dto.TokenDto;
import org.example.algosolve.user.exception.DuplicateUserIdException;
import org.example.algosolve.user.service.AuthService;
import org.example.algosolve.user.service.LoginInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "인증과 관련된 api")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @GetMapping("/issued_access_token")
    @Operation(summary = "엑세스 토큰 재발급", description = "엑세스 토큰 만료시 재발급 api")
    public TokenDto issuedAccessToken(HttpServletRequest httpServletRequest) {
        String refreshToken = tokenProvider.extractTokenFromHeader(httpServletRequest);
        String id = tokenProvider.extractUserId(refreshToken);
        return new TokenDto(authService.issueAccessToken(LocalDateTime.now(), id, refreshToken));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자 회원가입용 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (예: 필수 파라미터 누락, 유효하지 않은 데이터 등)",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value =  "{\n\"status\" : 400,\n \"occurredAt\" : \"2025-01-21T07:50:56.652Z\",\n \"messages\" : {\n \"passwordCheck\" : { \"message\" : \"비밀번호가 인증번호와 일치하지 않습니다\",\n \"rejectedValue\": \"asdfsadf1234\"\n}, \"level\" : { \"message\" : \"레벨은 5이하이여야 합니다\",\n \"rejectedValue\": \"6\"\n} \n}\n}"))),
            @ApiResponse(responseCode = "401", description = "중복된 사용자 ID",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n\"status\" : 401,\n \"occurredAt\" : \"2025-01-21T07:50:56.652Z\",\n \"message\" : \"아이디가 중복 됩니다.\"\n}")))
    })
    public void signup(@RequestBody @Validated SignupDto signupDto) {
        authService.signup(signupDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인 api")
    public TokenDto login(@RequestBody IdPasswordDto idPasswordDto, HttpServletResponse response) {
        LoginInfo loginInfo = authService.login(LocalDateTime.now(), idPasswordDto.getUserId(), idPasswordDto.getPassword());
        response.addCookie(tokenProvider.creatRefreshTokenCookie(loginInfo.getRefreshToken()));
        return new TokenDto(loginInfo.getAccessToken(), loginInfo.getNickName());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/checkId")
    @Operation(summary = "아이디 검사", description = "아이디 검사용 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 사용가능"),
            @ApiResponse(responseCode = "401", description = "중복된 사용자 ID",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n\"status\" : 401,\n \"occurredAt\" : \"2025-01-21T07:50:56.652Z\",\n \"message\" : \"아이디가 중복 됩니다.\"\n}")))
    })
    public void checkId(@RequestBody IdDto idDto){
        if(authService.containId(idDto.getId())){
            throw  new DuplicateUserIdException();
        }
    }
}
