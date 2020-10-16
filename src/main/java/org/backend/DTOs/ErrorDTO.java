package org.backend.DTOs;

public class ErrorDTO extends ResponseDTO {
    String message;

    public ErrorDTO (String errorMessage) {
        success = false;
        message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
