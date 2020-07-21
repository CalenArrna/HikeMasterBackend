package org.backend.Controllers;

import org.apache.tomcat.jni.File;
import org.backend.DTOs.ResponseDTO;
import org.backend.DTOs.SuccessDTO;
import org.backend.Model.HikeRoute;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseDTO createHikeRoute(@RequestParam("file")MultipartFile kml) {
        
        return new SuccessDTO();
    }

}
