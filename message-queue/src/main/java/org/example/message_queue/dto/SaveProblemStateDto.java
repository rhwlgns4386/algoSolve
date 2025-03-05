package org.example.message_queue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

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

    public Message toMessage(Long userId){
        return new Message(userId,platform,resultState,problemId,name,url,solvedDate);
    }
}
