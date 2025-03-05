package org.example.algosolve.global.exception.response;

import lombok.Data;
import org.example.algosolve.global.exception.response.ErrorMessage;

@Data
class SingMessageErrorErrorMessage implements ErrorMessage {
    private final String message;

    public SingMessageErrorErrorMessage(String message) {
        this.message = message;
    }
}
