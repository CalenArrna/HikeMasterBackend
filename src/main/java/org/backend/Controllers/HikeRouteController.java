package org.backend.Controllers;

import org.backend.Repository.HikeRouteRepository;
import org.backend.DTOs.HikeRouteErrorDTO;
import org.backend.DTOs.HikeRouteSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.HikeRoute;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;
    HikeRouteService messageService;
    HikeRouteRepository hikeRouteRepository;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService, HikeRouteService messageService,HikeRouteRepository hikeRouteRepository) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
        this.hikeRouteRepository=hikeRouteRepository;
    }

    @GetMapping(value = "/hike_route/{route_Id}")
    public HikeRoute getHikeRouteDetails(@PathVariable Long route_Id) {
        return hikeRouteService.hikeRouteDetails(route_Id);
    }

    @PostMapping(value = "/hike_route")
    public ResponseDTO postHikeRoute(String tour_type,String route_type,String difficultly,Integer tour_length,Integer level_rise,Integer rate){
        List<HikeRoute> routesByParams = hikeRouteService.findHikeRoutesByParams(tour_type, route_type, difficultly, tour_length, level_rise, rate);
        if(routesByParams.isEmpty()){
            return new HikeRouteErrorDTO();
        }else{
            HikeRouteSuccessDTO hikeRouteSuccessDTO= new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoutes(routesByParams);
            return hikeRouteSuccessDTO;
        }
    }

}
