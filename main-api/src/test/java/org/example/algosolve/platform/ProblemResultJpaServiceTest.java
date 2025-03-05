package org.example.algosolve.platform;

import jakarta.transaction.Transactional;
import org.example.algosolve.platform.domain.Platform;
import org.example.algosolve.platform.domain.ResultState;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.example.algosolve.platform.service.ProblemResultJpaService;
import org.example.algosolve.platform.service.ProblemResultRepository;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProblemResultJpaServiceTest {

    @Autowired
    private ProblemResultJpaService problemResultService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemResultRepository problemResultRepository;

    @Test
    void save() {
        User user = userRepository.save(TestUser.USER);
        Platform platform =Platform.BOJ;
        ResultState resultState = ResultState.AC;
        String problemId = "1";
        String name = "testName";
        String url = "www.test.com";

        problemResultService.save(new ProblemStateDto(1L,platform, resultState, problemId, name, url, LocalDateTime.now()));

        assertThat(problemResultRepository.count()).isEqualTo(1);
    }
}