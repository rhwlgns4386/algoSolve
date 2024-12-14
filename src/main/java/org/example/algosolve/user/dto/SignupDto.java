package org.example.algosolve.user.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.algosolve.user.domain.UserPasswordEncoder;
import org.example.algosolve.user.dto.validate.PasswordCheck;
import org.example.algosolve.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
public class SignupDto {
    @Valid
    @JsonUnwrapped
    private Credentials credentials;
    @Min(value = 1, message = "레벨은 1이상이여야 합니다")
    @Max(value = 5, message = "레벨은 5이하이여야 합니다")
    private int level;
    private String gitUrl;

    public SignupDto(String userId, String password, String passwordCheck, int level, String gitUrl) {
        this.credentials = new Credentials(userId, password, passwordCheck);
        this.level = level;
        this.gitUrl = gitUrl;
    }

    public User toEntity(UserPasswordEncoder passwordEncoder) {
        return new User(getUserId(), getPassword(), level, gitUrl, passwordEncoder);
    }

    public String getPassword() {
        return credentials.getPassword();
    }

    public String getUserId() {
        return credentials.getUserId();
    }

    @Data
    @NoArgsConstructor
    @PasswordCheck
    public static class Credentials {
        @Valid
        @JsonUnwrapped
        private IdPasswordDto idPasswordDto;

        private String passwordCheck;

        public Credentials(String userId, String password, String passwordCheck) {
            this.idPasswordDto = new IdPasswordDto(userId, password);
            this.passwordCheck = passwordCheck;
        }

        public boolean passwordMatched() {
            if (hasAnyNull()) {
                return false;
            }
            return idPasswordDto.isSamePassword(passwordCheck);
        }

        private boolean hasAnyNull() {
            return idPasswordDto == null || passwordCheck == null;
        }

        public String getPassword() {
            return idPasswordDto.getPassword();
        }

        public String getUserId() {
            return idPasswordDto.getUserId();
        }
    }
}
