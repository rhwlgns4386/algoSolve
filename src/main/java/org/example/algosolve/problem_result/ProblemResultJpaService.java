package org.example.algosolve.problem_result;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.platform.ProblemResultService;
import org.example.algosolve.platform.ProblemStateDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProblemResultJpaService implements ProblemResultService {

    private final ProblemResultRepository problemResultRepository;

    @Transactional
    @Override
    public void save(ProblemStateDto problemStateDto){
        ProblemResult problemResult = new ProblemResult(problemStateDto.getUser(), problemStateDto.getPlatform(), problemStateDto.getResultState(), problemStateDto.getName(), problemStateDto.getProblemId(), problemStateDto.getUrl());
        problemResultRepository.save(problemResult);
    }
}
