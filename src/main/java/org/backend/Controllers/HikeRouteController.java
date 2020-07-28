package org.backend.Controllers;

import org.backend.DTOs.*;
import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.backend.Service.HikeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;
    HikeRouteService messageService;
    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService, HikeRouteService messageService,HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
        this.hikeRouteRepository=hikeRouteRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping(value = "/hike_route/{route_Id}")
    public ResponseDTO getHikeRouteDetails(@PathVariable Long route_Id) {
        HikeRoute hikeRoute = hikeRouteService.hikeRouteDetails(route_Id);
        if (hikeRoute == null){
            return new HikeRouteErrorDTO("Itt is hiba van"); //TODO: also need valid message
        } else {
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoute(hikeRoute);
            return hikeRouteSuccessDTO;
        }
    }

    @PostMapping(value = "/hike_route")

    public ResponseDTO searchHikeRoute(@RequestBody HikeRouteDTO hikeRouteDTO) {
       List<HikeRoute> routesByParams = hikeRouteService.findHikeRoutesByParams(hikeRouteDTO);
        if (routesByParams.isEmpty()) {
            return new HikeRouteErrorDTO("Hiba van itt is"); //TODO: need valid error message
        } else {
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoutes(routesByParams);
            return hikeRouteSuccessDTO;
        }
    }

    @PostMapping(value = "/createHikeRoute")
    public ResponseDTO createHikeRoute(@RequestParam("file") MultipartFile kml) throws XMLStreamException {
        try {
            hikeRouteService.createNewHikeRouteFrom(kml);
            return new HikeRouteSuccessDTO();
        } catch (Exception exception) {
            return new HikeRouteErrorDTO(exception.getMessage());//TODO: make a proper error handling here!
        }
    }

    @PostMapping(value = "/rest/hike_route/area")
    public List<MarkerDTO> getHikeRouteListOfArea(@RequestParam double latitude, @RequestParam double longitude,
                                                  @RequestParam int radius) {
        return hikeRouteService.hikeRouteInArea(latitude, longitude, radius);
    }

    @RequestMapping(value = "/hike_route/{route_Id}/messages", method = RequestMethod.POST)
    public String addMessageToRoute(@PathVariable Long route_Id, @RequestBody Message message ) {
        if(hikeRouteRepository.findById(route_Id).isPresent()){
            hikeRouteRepository.findById(route_Id).get().getMessages().add(messageRepository.save(message));
            return "success";
        }
        else {
            return "failed";
        }
    }

    @PostMapping(value = "/hike_route/upload")
    public ResponseDTO addNewHikeRoute(@RequestBody HikeRouteDTO hikeRouteDTO){
        Long hikeRouteId = hikeRouteService.addNewHikeRoute(hikeRouteDTO);
        return new HikeRouteSuccessDTO(hikeRouteId);

    }
}
