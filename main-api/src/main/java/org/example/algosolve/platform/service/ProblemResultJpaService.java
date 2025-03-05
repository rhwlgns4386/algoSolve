package org.example.algosolve.platform.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.platform.ProblemResultService;
import org.example.algosolve.platform.domain.ProblemResult;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ProblemResultJpaService implements ProblemResultService {

    private final ProblemResultRepository problemResultRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void save(ProblemStateDto problemStateDto){
        LocalDate date = problemStateDto.getSolveDate().toLocalDate();
        User user = userRepository.findById(problemStateDto.getUserId()).get();
        ProblemResult problemResult = new ProblemResult(user, problemStateDto.getPlatform(), problemStateDto.getResultState(), problemStateDto.getName(), problemStateDto.getProblemId(), problemStateDto.getUrl(),date);
        problemResultRepository.save(problemResult);
    }
}
