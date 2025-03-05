package org.example.message_queue.config;

import org.example.message_queue.InMemoryMessageQueue;
import org.example.message_queue.MessageQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig {

    @Bean
    public MessageQueue messageQueue(){
        return new InMemoryMessageQueue();
    }
}
