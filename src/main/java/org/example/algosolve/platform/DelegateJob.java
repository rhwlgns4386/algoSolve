package org.example.algosolve.platform;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DelegateJob implements Runnable{

    private final AlgoSolvePlatformMessageQueue messageQueue;
    private final ProblemResultService problemResultService;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            problemResultService.save(messageQueue.take());
        }
    }
}
