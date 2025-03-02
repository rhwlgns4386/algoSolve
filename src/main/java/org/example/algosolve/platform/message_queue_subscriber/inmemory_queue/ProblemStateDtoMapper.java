package org.example.algosolve.platform.message_queue_subscriber.inmemory_queue;

import org.example.algosolve.in_memroy_queue.dto.Message;
import org.example.algosolve.platform.domain.Platform;
import org.example.algosolve.platform.domain.ResultState;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.example.algosolve.user.domain.User;

import java.time.LocalDateTime;

public class ProblemStateDtoMapper {
    public ProblemStateDto toProblemStateDto(Message message) {
        ProblemStateDtoBuilder builder = new ProblemStateDtoBuilder();
        return builder
                .user(message.getUser())
                .platform(message.getPlatform())
                .resultState(message.getResultState())
                .problemId(message.getProblemId())
                .name(message.getName())
                .url(message.getUrl())
                .solveDate(message.getSolveDate())
                .build();
    }

    private static class ProblemStateDtoBuilder {
        private User user;
        private Platform platform;
        private ResultState resultState;
        private String problemId;
        private String name;
        private String url;
        private LocalDateTime solveDate;

        ProblemStateDtoBuilder user(User user) {
            this.user = user;
            return this;
        }

        ProblemStateDtoBuilder platform(org.example.algosolve.in_memroy_queue.dto.Platform platform) {
            this.platform = toPlatForm(platform);
            return this;
        }

        private Platform toPlatForm(org.example.algosolve.in_memroy_queue.dto.Platform platform) {
            switch (platform) {
                case BOJ -> {
                    return Platform.BOJ;
                }
                case LEETCODE -> {
                    return Platform.LEETCODE;
                }
                case PROGRAMERS -> {
                    return Platform.PROGRAMERS;
                }
                default -> {
                    //todo 예외 처리 작업 해야함
                    throw new RuntimeException();
                }
            }
        }

        ProblemStateDtoBuilder resultState(org.example.algosolve.in_memroy_queue.dto.ResultState resultState) {
            this.resultState = toResultStatus(resultState);
            return this;
        }

        private ResultState toResultStatus(org.example.algosolve.in_memroy_queue.dto.ResultState resultState) {
            switch (resultState) {
                case AC -> {
                    return ResultState.AC;
                }
                case FAIL -> {
                    return ResultState.FAIL;
                }
                default -> {
                    //todo 예외 처리 작업 해야함
                    throw new RuntimeException();
                }
            }
        }

        ProblemStateDtoBuilder problemId(String problemId) {
            this.problemId = problemId;
            return this;
        }

        ProblemStateDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        ProblemStateDtoBuilder url(String url) {
            this.url = url;
            return this;
        }

        ProblemStateDtoBuilder solveDate(LocalDateTime solveDate) {
            this.solveDate = solveDate;
            return this;
        }

        ProblemStateDto build() {
            return new ProblemStateDto(user, platform, resultState, problemId, name, url, solveDate);
        }
    }
}
