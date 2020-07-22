package org.backend.Controllers;

import org.backend.DTOs.HikeRouteErrorDTO;
import org.backend.DTOs.HikeRouteSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import org.backend.Service.HikeRouteService;
import org.backend.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;
    MessageService messageService;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService, MessageService messageService) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
    }

    @GetMapping(value = "/hike_route/{route_Id}")
    public ResponseDTO getHikeRouteDetails(@PathVariable Long route_Id) {
        HikeRoute hikeRoute = hikeRouteService.hikeRouteDetails(route_Id);
        if (hikeRoute == null){
            return new HikeRouteErrorDTO();
        }
        else{
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoute(hikeRoute);
            return hikeRouteSuccessDTO;
        }
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
