package org.example.algosolve.platform.service;

import org.example.algosolve.platform.domain.ProblemResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemResultRepository extends JpaRepository<ProblemResult,Long> {
}
