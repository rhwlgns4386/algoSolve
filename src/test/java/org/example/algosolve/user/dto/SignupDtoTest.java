package org.example.algosolve.user.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.algosolve.user.dto.TestSignupDtoFactory.signupDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class SignupDtoTest extends ValidatorTest {


    @ParameterizedTest
    @NullAndEmptySource
    void 아이디가_빈값인경우_예외(String id) {
        SignupDto dto = signupDto(id, "testPassword", "testPassword", 1);

        assertThat(hasError(dto)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 비밀번호가_빈값인경우_예외(String password) {
        SignupDto dto = signupDto("test1", password, "testPassword", 1);

        assertThat(hasError(dto)).isTrue();
    }

    @Test
    void 인증_비밀번호와_비밀번호가_같지않으면_예외() {
        SignupDto dto = signupDto("test1", "testPassword1", "testPassword", 1);

        assertThat(hasError(dto)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void 레벨값이_범위밖이면예외(int level) {
        SignupDto dto = signupDto("test1", "testPassword", "testPassword", level);

        assertThat(hasError(dto)).isTrue();
    }
}
