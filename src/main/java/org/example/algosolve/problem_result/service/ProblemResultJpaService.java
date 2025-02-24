package org.example.algosolve.problem_result.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.platform.ProblemResultService;
import org.example.algosolve.platform.ProblemStateDto;
import org.example.algosolve.problem_result.entity.ProblemResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ProblemResultJpaService implements ProblemResultService {

    private final ProblemResultRepository problemResultRepository;

    @Transactional
    @Override
    public void save(ProblemStateDto problemStateDto){
        LocalDate date = problemStateDto.getSolveDate().toLocalDate();
        ProblemResult problemResult = new ProblemResult(problemStateDto.getUser(), problemStateDto.getPlatform(), problemStateDto.getResultState(), problemStateDto.getName(), problemStateDto.getProblemId(), problemStateDto.getUrl(),date);
        problemResultRepository.save(problemResult);
    }
}
