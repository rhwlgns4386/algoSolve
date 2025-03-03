package org.example.message_queue.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.message_queue.MessageQueue;
import org.example.message_queue.dto.SaveProblemStateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProblemResultController {

    private final MessageQueue inMemoryMessageQueue;

    @PostMapping("/api/v1/problem")
    public synchronized void save(@RequestBody SaveProblemStateDto saveProblemStateDto){
        inMemoryMessageQueue.add(saveProblemStateDto.toMessage(1L));
    }
}
