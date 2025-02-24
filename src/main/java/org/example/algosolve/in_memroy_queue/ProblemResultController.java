package org.example.algosolve.in_memroy_queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.user.domain.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProblemResultController {

    private final InMemoryMessageQueue inMemoryMessageQueue;
    private final UserRepository userRepository;
    private final AtomicInteger count = new AtomicInteger(0);

    @PostMapping("/api/v1/problem")
    public synchronized void save(@RequestBody SaveProblemStateDto saveProblemStateDto){
        inMemoryMessageQueue.add(saveProblemStateDto.problemStateDto(userRepository.findById(1L).get()));
    }
}
