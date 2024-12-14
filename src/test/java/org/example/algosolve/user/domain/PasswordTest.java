package org.example.algosolve.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.algosolve.user.TestUserPasswordEncoder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PasswordTest {

    @ParameterizedTest
    @CsvSource(value = {"test,true","tes,false"})
    void 비밀본호를_검증한다(String inputPassword,boolean result){
        UserPasswordEncoder userPasswordEncoder = new TestUserPasswordEncoder();
        Password password= new Password("test",userPasswordEncoder);

        assertThat(password.match(inputPassword,userPasswordEncoder)).isEqualTo(result);
    }

}
