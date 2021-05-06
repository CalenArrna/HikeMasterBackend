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
        this.hikeRouteRepository = hikeRouteRepository;

    }

    @PostMapping(value = "/registration")
    public ResponseDTO registration(@Valid @RequestBody RegisterDTO newUser, BindingResult bindingResult) {
        Authority userAuthority = service.getUserAuthority();
        ResponseDTO response = handleRequestValidations(newUser, bindingResult);
        if (response.getSuccess()) {
            return addValidUserToDatabase(newUser, userAuthority);
        } else {
            return response;
        }
    }

    private ResponseDTO handleRequestValidations(UserData data, BindingResult bindingResult) {
        PasswordData passwordData = mapper.map(data, PasswordData.class);
        ResponseDTO springValidation = validationService.validateSpringResults(bindingResult);

        if (data instanceof RegisterDTO && !validationService.validateUsername(passwordData)) {
            return HikeMasterUserErrorDTO.getUsernameAlreadyExistErrorDTO();
        }
        if (data.getEmail() != null && validationService.emailIsInDatabase(data)) {
            return HikeMasterUserErrorDTO.getEmailAlreadyExistErrorDTO();
        }

        ResponseDTO passwordValidation = null;
        if (data.getPassword() != null) {
            if (!data.getPassword().equals(data.getPasswordConfirm())) {
                return HikeMasterUserErrorDTO.getPasswordConfirmationErrorDTO();
            } else {
                passwordValidation = validationService.validatePassword(data);
            }
        }

        if (passwordValidation != null && passwordValidation.getSuccess()
                && springValidation.getSuccess()) {
            return new HikeMasterUserSuccessDTO();
        } else if (passwordValidation == null && springValidation.getSuccess()) {
            return new HikeMasterUserSuccessDTO();
        } else {
            return collectErrorsToDTO(passwordValidation, springValidation);
        }
    }

    private ResponseDTO addValidUserToDatabase(RegisterDTO newUser, Authority userAuthority) {
        HikeMasterUser validHikeMasterUser = mapper.map(newUser, HikeMasterUser.class);
        validHikeMasterUser.setPassword(encoder.encode(validHikeMasterUser.getPassword()));
//        validHikeMasterUser.setRole(userAuthority.getRoleName());
        validHikeMasterUser.getAuthoritySet().add(userAuthority);
//        userAuthority.getSecurityHikeMasterUsers().add(validHikeMasterUser);
        service.addUserToDatabase(validHikeMasterUser);
        return new HikeMasterUserSuccessDTO(validHikeMasterUser.getRole());
    }

    private ResponseDTO collectErrorsToDTO(ResponseDTO passwordValidation, ResponseDTO springValidation) {
        HikeMasterUserErrorDTO hikeMasterUserErrorDTO = new HikeMasterUserErrorDTO();
        mapper.map(springValidation, hikeMasterUserErrorDTO);
        if (passwordValidation != null && !passwordValidation.getSuccess()) {
            if (hikeMasterUserErrorDTO.getSuccess()) hikeMasterUserErrorDTO.setSuccess(false);
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
    public ResponseDTO editUserProfile(@RequestBody @Valid ProfileEditDTO changes, BindingResult validation) {
        ResponseDTO response = handleRequestValidations(changes, validation);
        if (response.getSuccess()) {
            if (changes.getPassword() != null) changes.setPassword(encoder.encode(changes.getPassword()));
            return service.editProfile(changes);
        } else {
            return response;
        }
    }


}
