package org.backend.Service;

import org.backend.DTOs.ErrorDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.SuccessDTO;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@Service
public class ValidationService {
    private PasswordValidator passwordValidator;
    private UserService userService;


    @Autowired
    public ValidationService(UserService us, PasswordValidator pv) {
        this.passwordValidator = pv;
        this.userService = us;
    }


    public ResponseDTO validatePassword(PasswordData passwordData) {
        RuleResult result = passwordValidator.validate(passwordData);
        if (result.isValid()) {
            return new SuccessDTO();
        } else {
            ErrorDTO errorDTO = new ErrorDTO();
            result.getDetails();
            return errorDTO;
        }
    }

    public boolean validateUsername(PasswordData passwordData) {
        return !userService.userExists(passwordData.getUsername());
    }
    
    public ResponseDTO validateSpringResults (BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDTO errorDTO = new ErrorDTO();
            
            return errorDTO;
        }else {
            return new SuccessDTO();
        }
    }
}
