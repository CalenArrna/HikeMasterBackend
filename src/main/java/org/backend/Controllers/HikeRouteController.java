package org.backend.Controllers;

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
    public HikeRoute getHikeRouteDetails(@PathVariable Long route_Id) {
        return hikeRouteService.hikeRouteDetails(route_Id);
    }

    @PostMapping(value = "/hike_route/{route_Id}/messages")
    public List<Message> addMessageToHikeRoute(Message message, HikeRoute hikeRoute){
      return messageService.addMessage(message, hikeRoute);
    }

}
