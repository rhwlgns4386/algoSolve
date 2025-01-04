package org.example.algosolve.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.algosolve.user.dto.TestSignupDtoFactory.signupDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import org.example.algosolve.user.TestUserPasswordEncoder;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.token.TokenEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    private static final String SIGNUP_PATH = "/auth/signup";
    private static final String LOGIN_PATH = "/auth/login";

    private static final String ISSUE_ACCESS_TOKEN = "/auth/issued_access_token";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenEncoder tokenEncoder;

    @Test
    void 회원가입_테스트() throws Exception {
        String userId = "test1";
        String password = "testPassword";
        String content = createSignupContent(userId, password, password, 1);

        mockMvc.perform(post(SIGNUP_PATH).contentType(MediaType.APPLICATION_PROBLEM_JSON).content(content))
                .andExpect(status().isOk());

        Optional<User> optionalUser = userRepository.findUserByUserId(userId);
        assertThat(optionalUser.isPresent()).isTrue();
    }

    @Test
    void 이미_사용중인_아이디이면_예외() throws Exception {
        userRepository.save(new User("test1", "testPassword", 1,new TestUserPasswordEncoder()));
        String content = createSignupContent("test1", "testPassword", "testPassword", 1);

        mockMvc.perform(post(SIGNUP_PATH).contentType(MediaType.APPLICATION_PROBLEM_JSON).content(content))
                .andExpect(status().isConflict()).andDo(print());
    }

    @Test
    void 로그인_테스트() throws Exception {
        String userId = "test1";
        String password = "testPassword";
        userRepository.save(new User(userId, password, 1,new TestUserPasswordEncoder()));

        IdPasswordDto idPasswordDto = new IdPasswordDto(userId, password);

        mockMvc.perform(post(LOGIN_PATH).contentType(MediaType.APPLICATION_PROBLEM_JSON).content(toString(idPasswordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty()).andDo(print());
    }

    @Test
    void 로그인_실패_테스트() throws Exception {
        String userId = "test1";
        String password = "testPassword";
        userRepository.save(new User(userId, password, 1,new TestUserPasswordEncoder()));

        IdPasswordDto idPasswordDto = new IdPasswordDto(userId, password+"1");

        mockMvc.perform(post(LOGIN_PATH).contentType(MediaType.APPLICATION_PROBLEM_JSON).content(toString(idPasswordDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 엑세스토큰_재발급() throws Exception {
        String userId = "test1";
        String password = "testPassword";
        User user = userRepository.save(new User(userId, password, 1, new TestUserPasswordEncoder()));

        String refresh = tokenEncoder.refresh(LocalDateTime.now(), user.getUserId());
        user.updateRefreshToken(refresh);
        mockMvc.perform(get(ISSUE_ACCESS_TOKEN).header(HttpHeaders.AUTHORIZATION,"Bearer "+refresh))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }

    @Test
    void 액세스토큰_발급불가() throws Exception {
        String userId = "test1";
        String password = "testPassword";
        User user = userRepository.save(new User(userId, password, 1, new TestUserPasswordEncoder()));

        String refresh = tokenEncoder.refresh(LocalDateTime.now(), user.getUserId());
        user.updateRefreshToken(refresh);
        mockMvc.perform(get(ISSUE_ACCESS_TOKEN).header(HttpHeaders.AUTHORIZATION,"Bearer "+refresh+1))
                .andExpect(status().isUnauthorized());
    }

    private static String createSignupContent(String userId, String password, String passwordCheck, int level)
            throws JsonProcessingException {
        return toString(signupDto(userId, password, passwordCheck, level));
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String toString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
