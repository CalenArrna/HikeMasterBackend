package org.backend.Controllers;


import org.backend.DTOs.*;
import org.backend.Model.HikeRoute;
import org.backend.Service.HikeRouteService;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamException;
import java.util.List;

@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService) {
        this.hikeRouteService = hikeRouteService;
    }

    @GetMapping(value = "/hike_route/{route_Id}")
    public HikeRoute getHikeRouteDetails(@PathVariable Long route_Id) {
        return hikeRouteService.hikeRouteDetails(route_Id);
    }

    @PostMapping(value = "/createHikeRoute")
    public ResponseDTO createHikeRoute(@RequestParam("file") MultipartFile kml) throws XMLStreamException {
        try {
            hikeRouteService.createNewHikeRouteFrom(kml);
            return new SuccessDTO();
        } catch (Exception exception) {
            return new HikeRouteErrorDTO(exception.getMessage());//TODO: make a proper error handling here!
        }
    }

    @PostMapping(value = "/rest/hike_route/area")
    public List<MarkerDTO> getHikeRouteListOfArea(@RequestParam double latitude, @RequestParam double longitude,
                                                  @RequestParam int radius) {
        return hikeRouteService.hikeRouteInArea(latitude, longitude, radius);
    }

}
