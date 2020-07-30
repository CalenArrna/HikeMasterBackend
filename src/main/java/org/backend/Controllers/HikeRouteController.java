package org.backend.Controllers;

import org.apache.catalina.connector.ClientAbortException;
import org.backend.DTOs.*;
import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import org.backend.Model.Pictures;
import org.backend.Repository.HikeMasterUserRepository;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.backend.Service.HikeRouteService;
import org.backend.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;
    MessageService messageService;
    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;
    HikeMasterUserRepository hikeMasterUserRepository;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService, MessageService messageService, HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository, HikeMasterUserRepository hikeMasterUserRepository) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
        this.hikeRouteRepository = hikeRouteRepository;
        this.messageRepository = messageRepository;
        this.hikeMasterUserRepository = hikeMasterUserRepository;
    }

    @GetMapping(value = "/hike_route/{route_Id}")
    public ResponseDTO getHikeRouteDetails(@PathVariable Long route_Id) {
        HikeRoute hikeRoute = hikeRouteService.getHikeRouteOf(route_Id);
        if (hikeRoute == null) {
            return new HikeRouteErrorDTO("Nincs ilyen Id val rendelkezö tura"); //TODO: also need valid message
        } else {
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoute(hikeRoute);
            return hikeRouteSuccessDTO;
        }
    }

    @PostMapping(value = "/hike_route")
    public ResponseDTO searchHikeRoute(@RequestBody HikeRouteDTO hikeRouteDTO) throws ClientAbortException {
        List<HikeRoute> routesByParams = hikeRouteService.findHikeRoutesByParams(hikeRouteDTO);
        if (routesByParams.isEmpty()) {
            return new HikeRouteErrorDTO("Hiba van itt is"); //TODO: need valid error message
        } else {
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoutes(routesByParams);
            return hikeRouteSuccessDTO;
        }
    }

    @PostMapping(value = "/kml/{route_Id}/upload")
    public ResponseDTO addKMLToHikeRouteOf(@PathVariable Integer route_Id, @RequestParam("file") MultipartFile kml) throws XMLStreamException {
        try {
            hikeRouteService.addKMLtoHikeRouteOf(route_Id, kml);
            return new HikeRouteSuccessDTO();
        } catch (Exception exception) {
            return new HikeRouteErrorDTO(exception.getMessage());//TODO: make a proper error handling here!
        }
    }

    @GetMapping(value = "/kml/{route_Id}")
    public String getKMLFileOf(@PathVariable Long route_Id) {
        return hikeRouteService.getKmlStringOf(route_Id);
    }

    @PostMapping(value = "/hike_route/area")
    public List<MarkerDTO> getHikeRouteListOfArea(@RequestBody MarkerInputDTO areaData) {
        return hikeRouteService.hikeRouteInArea(areaData);
    }


    @PostMapping(value = "/hike_route/upload")
    public ResponseDTO addNewHikeRoute(@RequestBody HikeRouteDTO hikeRouteDTO) {
        Long hikeRouteId = hikeRouteService.addNewHikeRoute(hikeRouteDTO);
        return new HikeRouteSuccessDTO(hikeRouteId);
    }

    @GetMapping(value = "/hike_route/{hikeRouteId}/images")
    public List<Pictures> getImagesByHikeRouteId(@PathVariable(value = "hikeRouteId") Long hikeRouteId) {
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteId);
        return hikeRoute.map(HikeRoute::getPicturesList).orElse(null);
    }


    // @PostMapping(value = "/hike_route/{route_Id}/route_wish")
    // public String addRouteToUserWishList(@PathVariable Long route_Id){
    //     if (hikeRouteService.addRouteToWishList(route_Id) != null){
    //         return "success";
    //     }
    //     return "fail";
    // }
}
