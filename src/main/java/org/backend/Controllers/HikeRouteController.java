package org.backend.Controllers;

import org.backend.Model.HikeRoute;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService) {
        this.hikeRouteService = hikeRouteService;
    }

    @GetMapping(value = "/hikeRouteId")
    public HikeRoute getHikeRouteDetails(long hikeRouteId) {
        return hikeRouteService.hikeRouteDetails(hikeRouteId);
    }

}
