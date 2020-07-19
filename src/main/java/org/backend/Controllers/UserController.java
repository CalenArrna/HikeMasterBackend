package org.backend.Controllers;

import org.backend.DTOs.HikeMasterUserErrorDTO;
import org.backend.DTOs.RegisterDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.HikeMasterUserSuccessDTO;
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
        if (!newUser.getPassword().equals(newUser.getPasswordConfirm())) {
            return HikeMasterUserErrorDTO.getPasswordConfirmationErrorDTO();
        }
        PasswordData passwordData = mapper.map(newUser,PasswordData.class);
        boolean usernameValid = validationService.validateUsername(passwordData);
        if (!usernameValid) {
            return HikeMasterUserErrorDTO.getUsernameAlreadyExistErrorDTO();
        }
        
        ResponseDTO passwordValidation = validationService.validatePassword(passwordData);
        ResponseDTO springValidation = validationService.validateSpringResults(bindingResult);
        
        if (passwordValidation instanceof HikeMasterUserSuccessDTO
                && springValidation instanceof HikeMasterUserSuccessDTO){
            HikeMasterUser validHikeMasterUser = mapper.map(newUser, HikeMasterUser.class);
            validHikeMasterUser.setPassword(encoder.encode(validHikeMasterUser.getPassword()));
            service.addUserToDatabase(validHikeMasterUser);
            return new HikeMasterUserSuccessDTO();
        }else {
            HikeMasterUserErrorDTO hikeMasterUserErrorDTO = new HikeMasterUserErrorDTO();
            mapper.map(springValidation, hikeMasterUserErrorDTO);
            assert passwordValidation instanceof HikeMasterUserErrorDTO;
            hikeMasterUserErrorDTO.setPassword(((HikeMasterUserErrorDTO) passwordValidation).getPassword());
            return hikeMasterUserErrorDTO;
        }
    }

    @RequestMapping (value = "/login",method = RequestMethod.POST)
    public HikeMasterUser userLogin(@RequestBody HikeMasterUser hikeMasterUser){
      return service.loginUser(hikeMasterUser.getUsername(),hikeMasterUser.getPassword());
    }
}
