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
    @GetMapping(value = "/hike_route")
    public List<HikeRoute> getHikeRoutes(@RequestParam(value = "length",required =false,defaultValue = "200") double length,
                                         @RequestParam(value = "level",required = false,defaultValue = "1") int level,
                                         @RequestParam(value = "difficultly",required = false,defaultValue = "") String difficultly,
                                         @RequestParam(value = "distance",required = false,defaultValue = "10") double distance,
                                         @RequestParam(value = "spectacle",required = false) Integer spectacle
                                         ){

        return hikeRouteService.findHikeRoutesByParams( length,level,difficultly,distance,spectacle);
    }
}
