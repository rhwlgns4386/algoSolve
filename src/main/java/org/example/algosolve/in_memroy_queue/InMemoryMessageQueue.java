package org.example.algosolve.in_memroy_queue;

import lombok.extern.slf4j.Slf4j;
import org.example.algosolve.in_memroy_queue.dto.Message;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class InMemoryMessageQueue implements MessageQueue {

    private BlockingQueue<Message> store;

    public InMemoryMessageQueue(BlockingQueue<Message> store) {
        this.store = store;
    }

    public InMemoryMessageQueue(){
        this(new LinkedBlockingQueue<>());
    }

    @Override
    public Message take() {
        try {
            return store.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            //todo : 적당한 예외 생성
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Message dto){
        store.add(dto);
    }
}
