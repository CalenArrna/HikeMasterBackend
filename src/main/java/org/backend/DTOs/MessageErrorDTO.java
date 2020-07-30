package org.backend.DTOs;

import javax.validation.constraints.NotEmpty;

public class MessageErrorDTO extends ResponseDTO {

    public MessageErrorDTO() {
        success = false;
    }
}
