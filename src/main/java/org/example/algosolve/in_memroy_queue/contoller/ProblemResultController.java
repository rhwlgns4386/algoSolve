package org.example.algosolve.in_memroy_queue.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.in_memroy_queue.MessageQueue;
import org.example.algosolve.in_memroy_queue.dto.SaveProblemStateDto;
import org.example.algosolve.user.domain.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProblemResultController {

    private final MessageQueue inMemoryMessageQueue;
    private final UserRepository userRepository;

    @PostMapping("/api/v1/problem")
    public synchronized void save(@RequestBody SaveProblemStateDto saveProblemStateDto){
        inMemoryMessageQueue.add(saveProblemStateDto.toMessage(userRepository.findById(1L).get()));
    }
}
