package org.backend.Controllers;

import org.backend.DTOs.HikeRouteErrorDTO;
import org.backend.DTOs.HikeRouteSuccessDTO;
import org.backend.DTOs.ResponseDTO;
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

import java.time.LocalDateTime;
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
        HikeRoute hikeRoute = hikeRouteService.hikeRouteDetails(route_Id);
        if (hikeRoute == null){
            return new HikeRouteErrorDTO();
        } else {
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoute(hikeRoute);
            return hikeRouteSuccessDTO;
        }
    }

    @PostMapping(value = "/hike_route")
    public ResponseDTO postHikeRoute(String tour_type, String route_type, String difficultly, Integer tour_length, Integer level_rise, Integer rate) {
        List<HikeRoute> routesByParams = hikeRouteService.findHikeRoutesByParams(tour_type, route_type, difficultly, tour_length, level_rise, rate);
        if (routesByParams.isEmpty()) {
            return new HikeRouteErrorDTO();
        } else {
            HikeRouteSuccessDTO hikeRouteSuccessDTO = new HikeRouteSuccessDTO();
            hikeRouteSuccessDTO.setHikeRoutes(routesByParams);
            return hikeRouteSuccessDTO;
        }
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
}
