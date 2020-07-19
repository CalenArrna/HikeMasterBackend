package org.backend.Controllers;

import org.backend.DTOs.ErrorDTO;
import org.backend.DTOs.RegisterDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.SuccessDTO;
import org.backend.Model.HikeMasterUser;
import org.backend.Service.UserService;
import org.backend.Service.ValidationService;
import org.dozer.DozerBeanMapper;
import org.passay.PasswordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    
    private DozerBeanMapper mapper;
    private UserService service;
    private PasswordEncoder encoder;
    private ValidationService validationService;

    @Autowired
    public UserController(DozerBeanMapper mapper, UserService service, PasswordEncoder encoder, ValidationService validationService) {
        this.mapper = mapper;
        this.service = service;
        this.encoder = encoder;
        this.validationService = validationService;
        this.service=service;
    }

    @PostMapping(value = "/registration")
    public ResponseDTO registration(@Valid @RequestBody RegisterDTO newUser, BindingResult bindingResult) {
        PasswordData passwordData = mapper.map(newUser,PasswordData.class);
        ResponseDTO passwordValidation = validationService.validatePassword(passwordData);
        boolean usernameValid = validationService.validateUsername(passwordData);
        ResponseDTO springValidation = validationService.validateSpringResults(bindingResult);
        
        if (passwordValidation instanceof SuccessDTO 
                && springValidation instanceof SuccessDTO
                && usernameValid){
            HikeMasterUser validHikeMasterUser = mapper.map(newUser, HikeMasterUser.class);
            validHikeMasterUser.setPassword(encoder.encode(validHikeMasterUser.getPassword()));
            service.addUserToDatabase(validHikeMasterUser);
            return new SuccessDTO();
        }else {
            ErrorDTO errorDTO = new ErrorDTO();
            mapper.map(springValidation, errorDTO);
            mapper.map(passwordValidation, errorDTO);
            return errorDTO;
        }
    }

    @RequestMapping (value = "/login",method = RequestMethod.POST)
    public HikeMasterUser userLogin(@RequestBody HikeMasterUser hikeMasterUser){
      return service.loginUser(hikeMasterUser.getUsername(),hikeMasterUser.getPassword());
    }
}