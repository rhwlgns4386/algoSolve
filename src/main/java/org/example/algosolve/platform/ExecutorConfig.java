package org.example.algosolve.platform;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfig {

    private final ProblemResultMessageQueueSubscriber messageQueue;
    private final ProblemResultService problemResultService;

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService( @Value("${problemResult.subscriber:3}") int problemResultSubscriberCount){
        ExecutorService executorService = Executors.newFixedThreadPool(problemResultSubscriberCount);
        for(int i = 0 ; i < problemResultSubscriberCount; i++){
            executorService.submit(new DelegateJob(messageQueue,problemResultService));
        }
        return executorService;
    }
}
