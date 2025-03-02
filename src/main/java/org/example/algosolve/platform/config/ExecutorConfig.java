package org.example.algosolve.platform.config;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.in_memroy_queue.MessageQueue;
import org.example.algosolve.platform.message_queue_subscriber.DelegateJob;
import org.example.algosolve.platform.message_queue_subscriber.inmemory_queue.InMemoryMessageQueueAdapter;
import org.example.algosolve.platform.message_queue_subscriber.ProblemResultMessageQueueSubscriber;
import org.example.algosolve.platform.ProblemResultService;
import org.example.algosolve.platform.message_queue_subscriber.inmemory_queue.ProblemStateDtoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfig {

    private final ProblemResultService problemResultService;
    private final MessageQueue messageQueue;

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService(@Value("${problemResult.subscriber:3}") int problemResultSubscriberCount) {
        ExecutorService executorService = Executors.newFixedThreadPool(problemResultSubscriberCount);
        for (int i = 0; i < problemResultSubscriberCount; i++) {
            executorService.submit(new DelegateJob(subscriber(), problemResultService));
        }
        return executorService;
    }

    @Bean
    public ProblemResultMessageQueueSubscriber subscriber() {
        return new InMemoryMessageQueueAdapter(messageQueue, new ProblemStateDtoMapper());
    }
}
