package org.example.algosolve.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.algosolve.platform.domain.Platform;
import org.example.algosolve.platform.domain.ResultState;
import org.example.algosolve.user.domain.User;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProblemStateDto {

    private User user;
    private Platform platform;
    private ResultState resultState;
    private String problemId;
    private String name;
    private String url;
    private LocalDateTime solveDate;

}
