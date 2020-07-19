package org.backend.Controllers;

import org.backend.Model.HikeRoute;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/hike_route")
    public List<HikeRoute> getHikeRoutes(@RequestParam(value = "tour_type",required = false,defaultValue = "") String tourType,
                                         @RequestParam(value = "route_type",required = false,defaultValue = "") String routeType,
                                         @RequestParam(value = "difficultly",required = false,defaultValue = "") String difficultly,
                                         @RequestParam(value = "length",required =false,defaultValue = "0") Integer length,
                                         @RequestParam(value = "level_rise",required = false,defaultValue = "0") Integer levelRise,
                                         @RequestParam(value = "rate",required = false,defaultValue = "0") Double rate
                                         ){

        return hikeRouteService.findHikeRoutesByParams( tourType,routeType,difficultly,length,levelRise,rate);

    }
}
