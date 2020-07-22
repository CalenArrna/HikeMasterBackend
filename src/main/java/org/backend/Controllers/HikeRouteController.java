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

    @GetMapping(value = "/hike_route")
    public ResponseDTO getHikeRoutes(@RequestParam(value = "tour_type",required = false,defaultValue = "") String tourType,
                                     @RequestParam(value = "route_type",required = false,defaultValue = "") String routeType,
                                     @RequestParam(value = "difficultly",required = false,defaultValue = "") String difficultly,
                                     @RequestParam(value = "tour_length",required =false) Integer length,
                                     @RequestParam(value = "level_rise",required = false) Integer levelRise,
                                     @RequestParam(value = "rate",required = false,defaultValue = "0") Double rate
                                         ){

        List<HikeRoute> hikeRoutesByParams = hikeRouteService.findHikeRoutesByParams(tourType, routeType, difficultly, length, levelRise, rate);
        if(hikeRoutesByParams.isEmpty()){
            return new HikeRouteErrorDTO();
        }else{
            HikeRouteSuccessDTO hikeRouteSuccessDTO= new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoutes(hikeRoutesByParams);
            return hikeRouteSuccessDTO;
        }

    }
}
