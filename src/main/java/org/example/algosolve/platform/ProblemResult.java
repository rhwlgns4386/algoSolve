package org.example.algosolve.platform;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.algosolve.user.domain.User;

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
    private Integer problemId;
    private String url;

    public ProblemResult(User user, Platform platform, ResultState resultState, String name, String problemId, String url) {
        this.user = user;
        this.platform = platform;
        this.resultState = resultState;
        this.name = name;
        this.problemId = Integer.parseInt(problemId);
        this.url = url;
    }
}
