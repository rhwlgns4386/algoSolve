package org.example.algosolve.platform.message_queue_subscriber;

import org.example.algosolve.platform.dto.ProblemStateDto;

public interface ProblemResultMessageQueueSubscriber {
    ProblemStateDto take();
}
