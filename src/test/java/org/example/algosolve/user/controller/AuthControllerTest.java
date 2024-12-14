package org.example.algosolve.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.algosolve.user.dto.TestSignupDtoFactory.signupDto;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.example.algosolve.user.TestUserPasswordEncoder;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    private static final String SIGNUP_PATH = "/auth/signup";
    private static final String LOGIN_PATH = "/auth/login";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

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
                .andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").exists())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty()).andDo(print());
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
