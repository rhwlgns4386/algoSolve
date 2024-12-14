package org.example.algosolve.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.validation.FieldError;

@Data
class FieldErrorMessages implements ErrorMessage {
    private final Map<String, FiledErrorMessage> messages;

    private FieldErrorMessages(Map<String, FiledErrorMessage> messages) {
        this.messages = messages;
    }

    FieldErrorMessages(List<FieldError> fieldErrors) {
        this(FiledErrorMessagesConverter.toMap(fieldErrors));
    }

    private static class FiledErrorMessagesConverter {

        private static final String FIELD_PATTERN = "\\.";

        private static Map<String, FiledErrorMessage> toMap(List<FieldError> fieldErrors) {
            HashMap<String, FiledErrorMessage> result = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                String field = FiledErrorMessagesConverter.lastFiledName(fieldError.getField());
                result.put(field, FiledErrorMessagesConverter.toMessage(fieldError));
            }
            return result;
        }

        private static String lastFiledName(String field) {
            String[] split = field.split(FIELD_PATTERN);
            return split[split.length - 1];
        }

        private static FiledErrorMessage toMessage(FieldError fieldError) {
            return new FiledErrorMessage(fieldError);
        }
    }

    @Data
    private static class FiledErrorMessage {
        private final String message;
        private final String rejectedValue;

        private FiledErrorMessage(FieldError fieldError) {
            this.message = fieldError.getDefaultMessage();
            this.rejectedValue = getRejectedValue(fieldError);
        }

        private static String getRejectedValue(FieldError fieldError) {
            return fieldError.getRejectedValue() == null ? null : fieldError.getRejectedValue().toString();
        }
    }

}
