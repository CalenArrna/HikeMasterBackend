package org.backend.Controllers;

import org.backend.DTOs.*;
import org.backend.Model.HikeRoute;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.ImageRepository;
import org.backend.Repository.KMLRepository;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ImageRepository imageRepository;

    @Autowired
    KMLRepository kmlRepository;

    @Autowired
    public AdminController(HikeRouteService hikeRouteService, HikeRouteService messageService) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;

    }

    @DeleteMapping("/hike_routes/{hikeRouteId}")
    public ResponseDTO deleteHikeRoute(@PathVariable Long hikeRouteId) {
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteId);
        if (hikeRoute.isPresent()) {
            Optional<HikeRoute> route = hikeRouteRepository.findById(hikeRouteId);

            if(route.isPresent()){
                imageRepository.removePicturesByHikeRoute(route.get());
                kmlRepository.removeKMLfileByHikeRoute(route.get());
            }
            return new HikeRouteSuccessDTO();

        } else {
            return new HikeRouteErrorDTO("Valami hiba van!"); // TODO: Need valid message!
        }
    }

    @GetMapping("/hike_routes")
    public List<HikeRoute> getAllHikeRoute() {
        return hikeRouteRepository.findAll();
    }

    @PutMapping("/hike_routes")
    public List<HikeRoute> modifyHikeRoute(@RequestBody HikeRouteDTO hikeRouteDTO) {
        hikeRouteRepository.save(hikeRouteService.updateHikeRoute(hikeRouteDTO));
        return hikeRouteRepository.findAll();
    }

    @PostMapping(value = "/image/approve")
    public ResponseEntity<Boolean> imageApprove(@RequestBody PictureDTO pictureDTO) {
        imageRepository.save(hikeRouteService.imageApproval(pictureDTO));
        return new ResponseEntity<Boolean>(hikeRouteService.imageApproval(pictureDTO).getApprove(), HttpStatus.ACCEPTED);
    }
}