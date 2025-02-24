package org.example.algosolve.in_memroy_queue;

import org.example.algosolve.platform.ProblemStateDto;

public interface ProblemResultMessageQueueProvider {
    void add(ProblemStateDto dto);
}
