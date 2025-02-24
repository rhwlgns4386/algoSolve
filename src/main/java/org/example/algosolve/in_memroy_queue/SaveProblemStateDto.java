package org.example.algosolve.in_memroy_queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.example.algosolve.platform.Platform;
import org.example.algosolve.platform.ProblemStateDto;
import org.example.algosolve.platform.ResultState;
import org.example.algosolve.user.domain.User;

@Getter
@Data
public class SaveProblemStateDto {

    private Platform platform;
    @JsonProperty("result_state")
    private ResultState resultState;
    @JsonProperty("problem_id")
    private String problemId;
    private String name;
    private String url;

    public ProblemStateDto problemStateDto(User user){
        return new ProblemStateDto(user,platform,resultState,problemId,name,url);
    }
}
