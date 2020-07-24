package org.backend.Service;

import org.backend.DTOs.HikeMasterUserErrorDTO;
import org.backend.DTOs.RegisterDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.HikeMasterUserSuccessDTO;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;


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
            return new HikeMasterUserSuccessDTO();
        } else {
            HikeMasterUserErrorDTO hikeMasterUserErrorDTO = new HikeMasterUserErrorDTO();
            List<RuleResultDetail> errorList = result.getDetails();
            int errorCount = errorList.size();
            String[] passwordErrors = new String[errorCount];
            for (int i = 0; i < errorCount; i++) {
                passwordErrors[i] = errorList.get(i).getErrorCode();
            }
            hikeMasterUserErrorDTO.setPassword(passwordErrors);
            return hikeMasterUserErrorDTO;
        }
    }

    public boolean validateUsername(PasswordData passwordData) {
        return !userService.userExists(passwordData.getUsername());
    }

    public boolean emailIsInDatabase(RegisterDTO newUser) {
        return userService.isEmailExists(newUser.getEmail());
    }

    public ResponseDTO validateSpringResults(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            HikeMasterUserErrorDTO hikeMasterUserErrorDTO = HikeMasterUserErrorDTO.getSpringErrorsDTO(errorList);
            return hikeMasterUserErrorDTO;
        } else {
            return new HikeMasterUserSuccessDTO();
        }
    }
}
