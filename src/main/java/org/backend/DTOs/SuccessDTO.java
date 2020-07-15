package org.backend.DTOs;

public class SuccessDTO implements ResponseDTO{
    
    Boolean success;

    public SuccessDTO() {
        this.success = true;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
