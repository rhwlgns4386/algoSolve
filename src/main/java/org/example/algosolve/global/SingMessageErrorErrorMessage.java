package org.example.algosolve.global;

import lombok.Data;

@Data
class SingMessageErrorErrorMessage implements ErrorMessage {
    private final String message;

    public SingMessageErrorErrorMessage(String message) {
        this.message = message;
    }
}
