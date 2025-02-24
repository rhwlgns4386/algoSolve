package org.example.algosolve.platform;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.algosolve.user.domain.User;

@Getter
@AllArgsConstructor
public class ProblemStateDto {

    private User user;
    private Platform platform;
    private ResultState resultState;
    private String problemId;
    private String name;
    private String url;

}
