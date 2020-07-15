package org.backend.Controllers;

import org.backend.Model.HikeMasterUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @PostMapping(value = "/registration")
    public HikeMasterUser registration(){
return null;
    }
}
