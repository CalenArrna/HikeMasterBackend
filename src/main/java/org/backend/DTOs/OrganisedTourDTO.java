package org.backend.DTOs;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class OrganisedTourDTO {
    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    private long hikeRouteId;

    @NotNull(message = "Please provide a dateTime.")
    private LocalDateTime beginningOfEvent;

    private String createdBy;

    public String getName() {
        return name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHikeRouteId() {
        return hikeRouteId;
    }

    public void setHikeRouteId(long hikeRouteId) {
        this.hikeRouteId = hikeRouteId;
    }

    public LocalDateTime getBeginningOfEvent() {
        return beginningOfEvent;
    }

    public void setBeginningOfEvent(LocalDateTime beginningOfEvent) {
        this.beginningOfEvent = beginningOfEvent;
    }
}
