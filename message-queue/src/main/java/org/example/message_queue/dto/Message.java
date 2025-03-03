package org.example.message_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Message {

    private Long userId;
    private Platform platform;
    private ResultState resultState;
    private String problemId;
    private String name;
    private String url;
    private LocalDateTime solveDate;

}
