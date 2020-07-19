package org.backend.DTOs;

public class HikeMasterUserSuccessDTO extends ResponseDTO{
     private String role;

    public HikeMasterUserSuccessDTO(String role) {
        this.success = true;
        this.role=role;
    }
    public HikeMasterUserSuccessDTO() {
        this.success = true;
    }

}
