package org.backend.Controllers;

import org.backend.Model.SecUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @PostMapping(value = "/registration")
    public SecUser registration(){
return null;
    }
}
