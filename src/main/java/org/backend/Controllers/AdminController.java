package org.backend.Controllers;

import org.backend.DTOs.HikeRouteErrorDTO;
import org.backend.DTOs.HikeRouteSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.HikeRoute;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ResponseDTO deleteHikeRoute(@PathVariable Long hikeRouteId) {
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteId);
        if (hikeRoute.isPresent()) {
            hikeRouteRepository.deleteById(hikeRouteId);
            return new HikeRouteSuccessDTO();

        }else {
            return new HikeRouteErrorDTO("Valami hiba van!"); // TODO: Need valid message!
        }


    }
    @GetMapping("/hike_routes")
    public List<HikeRoute> getAllHikeRoute() {
        return hikeRouteRepository.findAll();
    }

    @PutMapping("/hike_routes")
    public List<HikeRoute> modifyHikeRoute(@RequestBody HikeRoute hikeRoute) {
        hikeRouteRepository.save(hikeRoute);
        return hikeRouteRepository.findAll();
    }

}