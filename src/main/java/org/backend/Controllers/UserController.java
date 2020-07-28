package org.backend.Controllers;

import org.backend.DTOs.HikeMasterUserErrorDTO;
import org.backend.DTOs.HikeMasterUserSuccessDTO;
import org.backend.DTOs.RegisterDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.Authority;
import org.backend.Model.HikeMasterUser;
import org.backend.Service.UserService;
import org.backend.Service.ValidationService;
import org.dozer.DozerBeanMapper;
import org.passay.PasswordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        this.service = service;
    }

    @PostMapping(value = "/registration")
    public ResponseDTO registration(@Valid @RequestBody RegisterDTO newUser, BindingResult bindingResult) {
        Authority userAuthority = service.getUserAuthority();
        PasswordData passwordData = mapper.map(newUser, PasswordData.class);
        boolean usernameValid = validationService.validateUsername(passwordData);
        boolean emailExistInDatabase = validationService.emailIsInDatabase(newUser);
        ResponseDTO passwordValidation = validationService.validatePassword(passwordData);
        ResponseDTO springValidation = validationService.validateSpringResults(bindingResult);
        
        if (!newUser.getPassword().equals(newUser.getPasswordConfirm())) {
            return HikeMasterUserErrorDTO.getPasswordConfirmationErrorDTO();
        }
        if (!usernameValid) {
            return HikeMasterUserErrorDTO.getUsernameAlreadyExistErrorDTO();
        }
        if (emailExistInDatabase) {
            return HikeMasterUserErrorDTO.getEmailAlreadyExistErrorDTO();
        }
        
        if (passwordValidation.getSuccess()
                && springValidation.getSuccess()) {
            return addValidUserToDatabase(newUser, userAuthority);
        } else {
            return collectErrorsToDTO(passwordValidation,springValidation);
        }
    }

    private ResponseDTO addValidUserToDatabase(RegisterDTO newUser, Authority userAuthority) {
        HikeMasterUser validHikeMasterUser = mapper.map(newUser, HikeMasterUser.class);
        validHikeMasterUser.setPassword(encoder.encode(validHikeMasterUser.getPassword()));
        validHikeMasterUser.getAuthoritySet().add(userAuthority);
        userAuthority.getSecurityHikeMasterUsers().add(validHikeMasterUser);
        validHikeMasterUser.setRole(userAuthority.getRoleName());
        service.addUserToDatabase(validHikeMasterUser);
        return new HikeMasterUserSuccessDTO(validHikeMasterUser.getRole());
    }
    
    private ResponseDTO collectErrorsToDTO (ResponseDTO passwordValidation, ResponseDTO springValidation) {
        HikeMasterUserErrorDTO hikeMasterUserErrorDTO = new HikeMasterUserErrorDTO();
        mapper.map(springValidation, hikeMasterUserErrorDTO);
        if (!passwordValidation.getSuccess()) {
            hikeMasterUserErrorDTO.setPassword(((HikeMasterUserErrorDTO) passwordValidation).getPassword());
        }
        return hikeMasterUserErrorDTO;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDTO userLogin(@RequestBody HikeMasterUser hikeMasterUser) {
        List<HikeMasterUser> hikeMasterUser1 = service.loginUser(hikeMasterUser.getUsername(), hikeMasterUser.getPassword());
        if (hikeMasterUser1 != null) {
            return new HikeMasterUserSuccessDTO(hikeMasterUser1.get(0).getRole());
        } else {
            return new HikeMasterUserErrorDTO();
        }
    }
}
