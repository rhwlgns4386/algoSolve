package org.example.algosolve.user.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserPasswordEncoder;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.dto.validate.PasswordCheck;

@Data
@NoArgsConstructor
@Schema(title = "MEM_REQ_02 : 회원가입 DTO")
public class SignupDto {
    @Valid
    @JsonUnwrapped
    @Schema(description = "사용자 정보를 포함하는 객체")
    private Credentials credentials;
    @Min(value = 1, message = "레벨은 1이상이여야 합니다")
    @Max(value = 5, message = "레벨은 5이하이여야 합니다")
    @Schema(description = "학습 레벨",example = "1")
    private int level;
    @Schema(description = "깃 허브 주소",example = "https://github.com/rhwlgns4386?tab=repositories")
    private String gitUrl;
    @NotNull
    @Schema(description = "닉네임",example = "yeye.0011")
    private String nickName;

    public SignupDto(String userId, String password, String passwordCheck, String nickName ,int level, String gitUrl) {
        this.credentials = new Credentials(userId, password, passwordCheck);
        this.level = level;
        this.gitUrl = gitUrl;
        this.nickName = nickName;
    }

    public User toEntity(UserPasswordEncoder passwordEncoder) {
        return new User(getUserId(), getPassword(), nickName, level, gitUrl, passwordEncoder);
    }

    @Schema(description = "사용자 비밀번호", example = "asdfsadf123")
    public String getPassword() {
        return credentials.getPassword();
    }

    @Schema(description = "사용자 ID", example = "test123")
    public String getUserId() {
        return credentials.getUserId();
    }

    @Schema(description = "비밀번호 검사",example = "asdfsadf123")
    public String getPasswordCheck(){
        return credentials.getPasswordCheck();
    }

    @Data
    @NoArgsConstructor
    @PasswordCheck
    public static class Credentials {
        @Valid
        @JsonUnwrapped
        private IdPasswordDto idPasswordDto;

        @NotBlank
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
