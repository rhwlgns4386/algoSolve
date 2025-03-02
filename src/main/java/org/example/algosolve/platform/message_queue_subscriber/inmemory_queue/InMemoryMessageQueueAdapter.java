package org.example.algosolve.platform.message_queue_subscriber.inmemory_queue;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.in_memroy_queue.MessageQueue;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.example.algosolve.platform.message_queue_subscriber.ProblemResultMessageQueueSubscriber;

@RequiredArgsConstructor
public class InMemoryMessageQueueAdapter implements ProblemResultMessageQueueSubscriber {

    private final MessageQueue messageQueue;
    private final ProblemStateDtoMapper mapper;

    @Override
    public ProblemStateDto take() {
        return mapper.toProblemStateDto(messageQueue.take());
    }
}
