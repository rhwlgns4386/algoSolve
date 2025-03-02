package org.example.algosolve.global.exception.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ErrorResponse {
    private String status;
    @JsonUnwrapped
    private ErrorMessage errorMessage;
    private LocalDateTime occurredAt;

    private ErrorResponse(String status, ErrorMessage errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.occurredAt = LocalDateTime.now();
    }

    public static ErrorResponse of(String status, String message) {
        return new ErrorResponse(status, new SingMessageErrorErrorMessage(message));
    }

    public static ErrorResponse of(String status, List<FieldError> errors) {
        return new ErrorResponse(status, new FieldErrorMessages(errors));
    }

}
