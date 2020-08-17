package org.backend.Service;

import org.backend.DTOs.*;
import org.dozer.DozerBeanMapper;
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
    private DozerBeanMapper mapper;


    @Autowired
    public ValidationService(UserService us, PasswordValidator pv, DozerBeanMapper mapper) {
        this.mapper = mapper;
        this.passwordValidator = pv;
        this.userService = us;
    }


    public ResponseDTO validatePassword(UserData userData) {
        if (!userData.getPassword().equals(userData.getPasswordConfirm())) {
            return HikeMasterUserErrorDTO.getPasswordConfirmationErrorDTO();
        }

        PasswordData passwordData = mapper.map(userData, PasswordData.class);
        RuleResult validationResult = passwordValidator.validate(passwordData);

        if (validationResult.isValid()) {
            return new HikeMasterUserSuccessDTO();
        } else {
            return collectPasswordErrorsToDTO(validationResult);
        }
    }

    private ResponseDTO collectPasswordErrorsToDTO(RuleResult result) {
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

    public boolean validateUsername(PasswordData passwordData) {
        return !userService.userExists(passwordData.getUsername());
    }

    public boolean emailIsInDatabase(UserData data) {
        return userService.isEmailExists(data.getEmail());
    }

    public ResponseDTO validateSpringResults(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return HikeMasterUserErrorDTO.getSpringErrorsDTO(errorList);
        } else {
            return new HikeMasterUserSuccessDTO();
        }
    }
}
