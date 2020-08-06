package org.backend.DTOs;

import org.locationtech.jts.geom.Coordinate;

import java.time.LocalDateTime;
import java.util.List;

public class DateAndCoordinatesDTO {
   private List<Coordinate> coordinates;
   private LocalDateTime dateOfRoute;

    public DateAndCoordinatesDTO() {
    }

    public DateAndCoordinatesDTO(List<Coordinate> coordinates, LocalDateTime dateOfRoute) {
        this.coordinates = coordinates;
        this.dateOfRoute = dateOfRoute;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getDateOfRoute() {
        return dateOfRoute;
    }

    public void setDateOfRoute(LocalDateTime dateOfRoute) {
        this.dateOfRoute = dateOfRoute;
    }
}
