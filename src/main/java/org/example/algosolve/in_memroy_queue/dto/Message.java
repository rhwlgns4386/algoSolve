package org.example.algosolve.in_memroy_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.algosolve.user.domain.User;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Message {

    private User user;
    private Platform platform;
    private ResultState resultState;
    private String problemId;
    private String name;
    private String url;
    private LocalDateTime solveDate;

}
