package org.backend.DTOs;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorDTO extends ResponseDTO{
    String[] username;
    String[] email;
    String[] password;
    String[] fullName;
    
    public ErrorDTO() {
        this.success = false;
    }

    public String[] getUsername() {
        return username;
    }

    public void setUsername(String[] username) {
        this.username = username;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getPassword() {
        return password;
    }

    public void setPassword(String[] password) {
        this.password = password;
    }

    public String[] getFullName() {
        return fullName;
    }

    public void setFullName(String[] fullName) {
        this.fullName = fullName;
    }
    
    public static ErrorDTO getPasswordConfirmationErrorDTO () {
        String[] errorMessage = new String[1];
        errorMessage[0] = "password and confirmation password do not match";
        ErrorDTO passConfError =  new ErrorDTO();
        passConfError.setPassword(errorMessage);
        return passConfError;
    }

    public static ErrorDTO getSpringErrorsDTO (List<ObjectError> errorList) {
        ErrorDTO springErrors = new ErrorDTO();
        for (ObjectError objectError : errorList) {
            switch (((FieldError)objectError).getField()){
                case "username" : 
                    String[] errorUsername = new String[1];
                    errorUsername[0] = objectError.getDefaultMessage();
                    springErrors.setUsername(errorUsername);
                    break;
                case "email":
                    String[] errorEmail = new String[1];
                    errorEmail[0] = objectError.getDefaultMessage();
                    springErrors.setEmail(errorEmail);
                    break;
                case "fullName":
                    String[] errorFullName = new String[1];
                    errorFullName[0] = objectError.getDefaultMessage();
                    springErrors.setFullName(errorFullName);
                    break;
            }
        }
        return springErrors;
    }
}
