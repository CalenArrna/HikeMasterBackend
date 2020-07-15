package org.backend.Controllers;

import org.backend.Model.HikeRoute;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HikeController {
    private HikeRouteService hikeRouteService;
    @Autowired
    public HikeController(HikeRouteService hikeRouteService) {
        this.hikeRouteService=hikeRouteService;
    }
    @GetMapping(value = "/hike_routes")
    public List<HikeRoute> getHikeRoutes(@RequestParam(value = "length",required = false) double length,
                                         @RequestParam(value = "level",required = false) int level,
                                         @RequestParam(value = "difficultly",required = false) String difficultly,
                                         @RequestParam(value = "distance",required = false) double distance,
                                         @RequestParam(value = "spectacle",required = false) String spectacle
                                         ){

        return hikeRouteService.findHikeRoutesByParams( length,level,difficultly,distance,spectacle);
    }
}
