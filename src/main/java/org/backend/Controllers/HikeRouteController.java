package org.backend.Controllers;

import org.backend.DTOs.*;
import org.backend.Model.HikeMasterUser;
import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import org.backend.Repository.HikeMasterUserRepository;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.backend.Service.HikeRouteService;
import org.backend.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import javax.xml.stream.XMLStreamException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;
    MessageService messageService;
    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;
    HikeMasterUserRepository hikeMasterUserRepository;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService, MessageService messageService,HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository, HikeMasterUserRepository hikeMasterUserRepository) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
        this.hikeRouteRepository=hikeRouteRepository;
        this.messageRepository = messageRepository;
        this.hikeMasterUserRepository = hikeMasterUserRepository;
    }

    @GetMapping(value = "/hike_route/{route_Id}")
    public ResponseDTO getHikeRouteDetails(@PathVariable Long route_Id) {
        HikeRoute hikeRoute = hikeRouteService.getHikeRouteOf(route_Id);
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

    @PostMapping(value = "/kml/{route_Id}/upload")
    public ResponseDTO addKMLToHikeRouteOf(@PathVariable Integer route_Id, @RequestParam("file") MultipartFile kml) throws XMLStreamException {
        try {
            hikeRouteService.addKMLtoHikeRouteOf(route_Id,kml);
            return new HikeRouteSuccessDTO();
        } catch (Exception exception) {
            return new HikeRouteErrorDTO(exception.getMessage());//TODO: make a proper error handling here!
        }
    }

    @PostMapping(value = "/hike_route/area")
    public List<MarkerDTO> getHikeRouteListOfArea(@RequestBody MarkerInputDTO areaData) {
        return hikeRouteService.hikeRouteInArea(areaData);
    }

    @RequestMapping(value = "/hike_route/{route_Id}/messages", method = RequestMethod.POST)
    public String addMessageToRoute(@PathVariable Long route_Id, @RequestBody Message message ) {
        if (hikeRouteRepository.findById(route_Id).isPresent()) {
            message.setHikeRoute(hikeRouteRepository.findById(route_Id).get());
            message.setMessageDate(LocalDateTime.now());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();

            if (principal instanceof HikeMasterUser){
                HikeMasterUser hikeMasterUser = (HikeMasterUser) principal;
                message.setHikeMasterUser(hikeMasterUser);
            }
            if (principal instanceof OidcUser){
                OidcUser oidcUser= (OidcUser) principal;
                message.setHikeMasterUser((HikeMasterUser)oidcUser);
            }
            hikeRouteRepository.findById(route_Id).get().getMessages().add(messageRepository.save(message));
            return "success";
        } else {
            return "failed";
        }
    }
    
    @GetMapping(value = "/hike_route/{route_Id}/kml")
    public String getKMLOf(@PathVariable Long route_Id, @RequestBody Message message ) {
        if(hikeRouteRepository.findById(route_Id).isPresent()){
            hikeRouteRepository.findById(route_Id).get().getMessages().add(messageRepository.save(message));
            return "success";
        }
        else {
            return "failed";
        }
    }
}
