package org.example.algosolve.platform;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.problem_result.ProblemResultJpaService;

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
