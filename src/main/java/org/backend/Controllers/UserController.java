package org.backend.Controllers;

import org.backend.DTOs.ErrorDTO;
import org.backend.DTOs.RegisterDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.SuccessDTO;
import org.backend.Model.HikeMasterUser;
import org.backend.Service.UserService;
import org.backend.Service.ValidationService;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.DozerBuilder;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.passay.PasswordData;
import org.postgis.Geometry;
import org.postgis.GeometryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    }

    @PostMapping(value = "/registration")
    public ResponseDTO registration(@Valid @RequestBody RegisterDTO newUser, BindingResult bindingResult) {
        if (!newUser.getPassword().equals(newUser.getPasswordConfirm())) {
            return ErrorDTO.getPasswordConfirmationErrorDTO();
        }
        PasswordData passwordData = mapper.map(newUser,PasswordData.class);
        boolean usernameValid = validationService.validateUsername(passwordData);
        if (!usernameValid) {
            return ErrorDTO.getUsernameAlreadyExistErrorDTO();
        }
        ResponseDTO passwordValidation = validationService.validatePassword(passwordData);
        ResponseDTO springValidation = validationService.validateSpringResults(bindingResult);
       
        if (passwordValidation instanceof SuccessDTO 
                && springValidation instanceof SuccessDTO){
            HikeMasterUser validHikeMasterUser = mapper.map(newUser, HikeMasterUser.class);
            validHikeMasterUser.setPassword(encoder.encode(validHikeMasterUser.getPassword()));
            service.addUserToDatabase(validHikeMasterUser);
            return new SuccessDTO();
        }else {
            ErrorDTO errorDTO = new ErrorDTO();
            mapper.map(springValidation, errorDTO);
            assert passwordValidation instanceof ErrorDTO;
            errorDTO.setPassword(((ErrorDTO) passwordValidation).getPassword());
            return errorDTO;
        }
    }
}
