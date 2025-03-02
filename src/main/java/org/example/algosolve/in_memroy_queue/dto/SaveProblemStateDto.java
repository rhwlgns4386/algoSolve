package org.example.algosolve.in_memroy_queue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.example.algosolve.platform.domain.Platform;
import org.example.algosolve.platform.dto.ProblemStateDto;
import org.example.algosolve.platform.domain.ResultState;
import org.example.algosolve.user.domain.User;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("solved_date")
    private LocalDateTime solvedDate;

    public ProblemStateDto problemStateDto(User user){
        return new ProblemStateDto(user,platform,resultState,problemId,name,url,solvedDate);
    }
}
