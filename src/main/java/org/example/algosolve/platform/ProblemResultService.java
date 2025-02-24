package org.example.algosolve.platform;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProblemResultService {

    private final ProblemResultRepository problemResultRepository;

    @Transactional
    public void save(ProblemStateDto problemStateDto){
        ProblemResult problemResult = new ProblemResult(problemStateDto.getUser(), problemStateDto.getPlatform(), problemStateDto.getResultState(), problemStateDto.getName(), problemStateDto.getProblemId(), problemStateDto.getUrl());
        problemResultRepository.save(problemResult);
    }
}
