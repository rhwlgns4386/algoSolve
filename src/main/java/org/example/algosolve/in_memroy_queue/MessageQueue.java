package org.example.algosolve.in_memroy_queue;

import org.example.algosolve.in_memroy_queue.dto.Message;
import org.example.algosolve.platform.dto.ProblemStateDto;

public interface MessageQueue {
    Message take();

    void add(Message dto);
}
