package org.backend.Controllers;

import org.backend.DTOs.*;
import org.backend.Model.Authority;
import org.backend.Model.HikeMasterUser;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Service.UserService;
import org.backend.Service.ValidationService;
import org.dozer.DozerBeanMapper;
import org.passay.PasswordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    private DozerBeanMapper mapper;
    private UserService service;
    private PasswordEncoder encoder;
    private ValidationService validationService;
    HikeRouteRepository hikeRouteRepository;

    @Autowired
    public UserController(DozerBeanMapper mapper, UserService service, PasswordEncoder encoder, ValidationService validationService, HikeRouteRepository hikeRouteRepository) {
        this.mapper = mapper;
        this.service = service;
        this.encoder = encoder;
        this.validationService = validationService;
        this.service = service;
        this.hikeRouteRepository = hikeRouteRepository;

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
            return collectErrorsToDTO(passwordValidation, springValidation);
        }
    }

    private ResponseDTO addValidUserToDatabase(RegisterDTO newUser, Authority userAuthority) {
        HikeMasterUser validHikeMasterUser = mapper.map(newUser, HikeMasterUser.class);
        validHikeMasterUser.setPassword(encoder.encode(validHikeMasterUser.getPassword()));
        validHikeMasterUser.setRole(userAuthority.getRoleName());
        validHikeMasterUser.getAuthoritySet().add(userAuthority);
        userAuthority.getSecurityHikeMasterUsers().add(validHikeMasterUser);
        service.addUserToDatabase(validHikeMasterUser);
        return new HikeMasterUserSuccessDTO(validHikeMasterUser.getRole());
    }

    private ResponseDTO collectErrorsToDTO(ResponseDTO passwordValidation, ResponseDTO springValidation) {
        HikeMasterUserErrorDTO hikeMasterUserErrorDTO = new HikeMasterUserErrorDTO();
        mapper.map(springValidation, hikeMasterUserErrorDTO);
        if (!passwordValidation.getSuccess()) {
            hikeMasterUserErrorDTO.setPassword(((HikeMasterUserErrorDTO) passwordValidation).getPassword());
        }
        return hikeMasterUserErrorDTO;
    }

    @GetMapping(value = "/user_role")
    public String getUser() {
        if (service.getHikeMasterUser() != null) {
            return service.getHikeMasterUser();
        } else {
            return "fail";
        }
    }

    @PostMapping(value = "/hike_route/{route_Id}/wish_list")
    public ResponseDTO addRouteToUserWishList(@PathVariable Long route_Id) {
        return service.addRouteToWishList(route_Id);
    }

    @GetMapping(value = "user/profile")
    public UserProfileDTO getUserData() {
        return convertHikeMasterUserToDTO(service.getSignedInHikeMasterUser());
    }

    private UserProfileDTO convertHikeMasterUserToDTO(HikeMasterUser user) {
        return mapper.map(user, UserProfileDTO.class);
    }

    @PostMapping(value = "user/profile/edit")
    public ResponseDTO editUserProfile(@RequestBody @Valid ProfileEditDTO changes, BindingResult validation) { //TODO: Put in password validation
        if (!changes.getPassword().equals(changes.getPasswordConfirm())) {
            HikeMasterUserErrorDTO error = new HikeMasterUserErrorDTO();//TODO: Separate validation from RequestHandling, remove duplication
            error.setPasswordConfirm(new String[]{"Passwords don't match!"});
            return error;
        }

        if (changes.getPassword() != null) changes.setPassword(encoder.encode(changes.getPassword()));

        if (validation.hasErrors()) {
            return validationService.validateSpringResults(validation);
        } else {
            return service.editProfile(changes);
        }
    }
}
