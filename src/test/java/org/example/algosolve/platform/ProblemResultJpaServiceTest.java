package org.example.algosolve.platform;

import jakarta.transaction.Transactional;
import org.example.algosolve.problem_result.service.ProblemResultRepository;
import org.example.algosolve.problem_result.service.ProblemResultJpaService;
import org.example.algosolve.user.TestUser;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        problemResultService.save(new ProblemStateDto(user,platform, resultState, problemId, name, url));

        assertThat(problemResultRepository.count()).isEqualTo(1);
    }
}