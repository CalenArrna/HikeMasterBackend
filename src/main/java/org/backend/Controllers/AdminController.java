package org.backend.Controllers;

import org.backend.Model.HikeRoute;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Model.Message;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    HikeRouteService hikeRouteService;
    HikeRouteService messageService;
    @Autowired
    HikeRouteRepository hikeRouteRepository;

    @Autowired
    public AdminController(HikeRouteService hikeRouteService, HikeRouteService messageService) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;

    }
    @DeleteMapping("/hike_routes/{hikeRouteId}")
    public String deleteHikeRoute(@PathVariable long hikeRouteId){
        hikeRouteRepository.deleteById(hikeRouteId);
        return "Törlés sikeres!";

    }
    @GetMapping("/hike_routes")
    public List<HikeRoute> getAllHikeRoute(){
        return hikeRouteRepository.findAll();
    }
    @PutMapping("/hike_routes/{hikeRouteId}/{mesaage}")
    public String modifyHikeRoute(@PathVariable Long hikeRoutId,@PathVariable Message message){
    return "Módosítás sikeres!";
    }
}