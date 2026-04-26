package az.cargora.cargora.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private int status;
    private String error;
    private List<String> messages;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(int status, String error, List<String> messages) {
        this.status = status;
        this.error = error;
        this.messages = messages;
        this.timestamp = LocalDateTime.now();
    }
}
