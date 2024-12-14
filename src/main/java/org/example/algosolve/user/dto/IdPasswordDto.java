package org.example.algosolve.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IdPasswordDto {
    @NotBlank(message = "아이디를 입력해주세요")
    private final String userId;
    @NotBlank(message = "비밀번호를 입력해주세요")
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
