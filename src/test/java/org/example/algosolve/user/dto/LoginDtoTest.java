package org.example.algosolve.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class LoginDtoTest extends ValidatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    void 아이디가_빈값인경우_예외(String id) {
        IdPasswordDto dto = new IdPasswordDto(id, "testPassword");

        assertThat(hasError(dto)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 비밀번호가_빈값인경우_예외(String password) {
        IdPasswordDto dto = new IdPasswordDto("test1", password);

        assertThat(hasError(dto)).isTrue();
    }


}
