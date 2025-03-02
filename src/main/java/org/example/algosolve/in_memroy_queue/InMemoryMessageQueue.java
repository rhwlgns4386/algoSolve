package org.example.algosolve.in_memroy_queue;

import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.platform.message_queue_subscriber.ProblemResultMessageQueueSubscriber;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class InMemoryMessageQueue implements ProblemResultMessageQueueSubscriber, ProblemResultMessageQueueProvider {

    private BlockingQueue<ProblemStateDto> store;

    public InMemoryMessageQueue(BlockingQueue<ProblemStateDto> store) {
        this.store = store;
    }

    public InMemoryMessageQueue(){
        this(new LinkedBlockingQueue<>());
    }

    @Override
    public ProblemStateDto take() {
        try {
            return store.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            //todo : 적당한 예외 생성
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(ProblemStateDto dto){
        store.add(dto);
    }
}
