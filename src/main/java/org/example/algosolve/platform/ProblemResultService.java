package org.example.algosolve.platform;

import jakarta.transaction.Transactional;

public interface ProblemResultService {
    @Transactional
    void save(ProblemStateDto problemStateDto);
}
