package org.example.algosolve.global;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ErrorResponse {
    private int status;
    @JsonUnwrapped
    private ErrorMessage errorMessage;
    private LocalDateTime timestamp;

    private ErrorResponse(int status, ErrorMessage errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, new SingMessageErrorErrorMessage(message));
    }

    static ErrorResponse of(int status, List<FieldError> errors) {
        return new ErrorResponse(status, new FieldErrorMessages(errors));
    }

}
