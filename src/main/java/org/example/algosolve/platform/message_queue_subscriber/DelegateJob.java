package org.example.algosolve.platform.message_queue_subscriber;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.platform.ProblemResultService;

@RequiredArgsConstructor
public class DelegateJob implements Runnable{

    private final ProblemResultMessageQueueSubscriber messageQueue;
    private final ProblemResultService problemResultService;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            problemResultService.save(messageQueue.take());
        }
    }
}
