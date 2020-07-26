package org.backend.DTOs;

public class HikeRouteErrorDTO extends ResponseDTO {
    String errorMessage;

    public HikeRouteErrorDTO(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }
}
