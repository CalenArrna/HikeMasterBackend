package org.backend.DTOs;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

public class RegisterDTO {
    @NotNull
    @Size(min = 3, max = 30)
    private String username;
    
    @NotNull
    @Size(min = 5, max = 30)
    private String fullName;
   
    @NotNull
    @Email
    private String email;
    
   // @NotNull
   // private Boolean notification;
    
    private String password;
  
    private String passwordConfirm;

    public RegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   // public boolean isNotification() {
   //     return notification;
   // }
//
   // public void setNotification(boolean notification) {
   //     this.notification = notification;
   // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
