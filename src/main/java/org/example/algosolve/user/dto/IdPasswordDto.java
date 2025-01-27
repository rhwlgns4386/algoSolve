package org.example.algosolve.user.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(title = "MEM_REQ_01 : 아이디와 비밀번호요청 DTO")
public class IdPasswordDto {
    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(description = "사용자 ID", example = "test123")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "사용자 비밀번호", example = "asdfsadf123")
    private final String password;

    public IdPasswordDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public boolean isSamePassword(String passwordCheck) {
        if(password==null) return false;
        return this.password.equals(passwordCheck);
    }
}
