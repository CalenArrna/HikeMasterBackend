package org.backend.DTOs;

public abstract class ResponseDTO {
    Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
