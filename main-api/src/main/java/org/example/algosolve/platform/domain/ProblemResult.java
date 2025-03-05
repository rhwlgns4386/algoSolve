package org.example.algosolve.platform.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.algosolve.user.domain.User;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class ProblemResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.STRING)
    private Platform platform;
    @Enumerated(value = EnumType.STRING)
    private ResultState resultState;
    private String name;
    private String problemId;
    private String url;
    private LocalDate solvedDate;

    public ProblemResult(User user, Platform platform, ResultState resultState, String name, String problemId, String url, LocalDate solvedDate) {
        this.user = user;
        this.platform = platform;
        this.resultState = resultState;
        this.name = name;
        this.problemId = problemId;
        this.url = url;
        this.solvedDate = solvedDate;
    }
}
