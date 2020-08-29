package org.backend.DTOs;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class OrganisedTourErrorDTO extends ResponseDTO {
    private String name;

    private String hikeRouteId;

    private String beginningOfEvent;

    public String getBeginningOfEvent() {
        return beginningOfEvent;
    }

    public void setBeginningOfEvent(String beginningOfEvent) {
        this.beginningOfEvent = beginningOfEvent;
    }


    public OrganisedTourErrorDTO() {
        success = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHikeRouteId() {
        return hikeRouteId;
    }

    public void setHikeRouteId(String hikeRouteId) {
        this.hikeRouteId = hikeRouteId;
    }

    public static OrganisedTourErrorDTO collectSpringErrorsFrom(BindingResult bindingResult) {
        OrganisedTourErrorDTO errors = new OrganisedTourErrorDTO();
        List<ObjectError> errorList = bindingResult.getAllErrors();
        for (ObjectError error : errorList) {
            switch (((FieldError) error).getField()) {
                case "name":
                    errors.setName(error.getDefaultMessage());
                    break;
                case "hikeRouteId":
                    errors.setHikeRouteId(error.getDefaultMessage());
                    break;
                case "beginningOfEvent":
                    errors.setBeginningOfEvent(error.getDefaultMessage());
                    break;
            }
        }
        return errors;
    }
}
