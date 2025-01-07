package org.example.algosolve.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private final String accessToken;
    private String nickName;

    public TokenDto(String accessToken){
        this(accessToken, null);
    }
}
