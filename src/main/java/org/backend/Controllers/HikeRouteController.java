package org.backend.Controllers;

import org.backend.DTOs.*;
import org.backend.Model.HikeMasterUser;
import org.backend.Model.HikeRoute;
import org.backend.Model.OrganisedTour;
import org.backend.Model.PictureURL;
import org.backend.Repository.HikeMasterUserRepository;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.MessageRepository;
import org.backend.Repository.OrganisedTourRepository;
import org.backend.Service.HikeRouteService;
import org.backend.Service.MessageService;
import org.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.stream.XMLStreamException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HikeRouteController {

    HikeRouteService hikeRouteService;
    UserService userService;
    MessageService messageService;
    HikeRouteRepository hikeRouteRepository;
    MessageRepository messageRepository;
    HikeMasterUserRepository hikeMasterUserRepository;
    OrganisedTourRepository organisedTourRepository;

    @Autowired
    public HikeRouteController(HikeRouteService hikeRouteService,UserService userService, OrganisedTourRepository organisedTourRepository ,MessageService messageService, HikeRouteRepository hikeRouteRepository, MessageRepository messageRepository, HikeMasterUserRepository hikeMasterUserRepository) {
        this.hikeRouteService = hikeRouteService;
        this.messageService = messageService;
        this.hikeRouteRepository = hikeRouteRepository;
        this.messageRepository = messageRepository;
        this.hikeMasterUserRepository = hikeMasterUserRepository;
        this.userService = userService;
        this.organisedTourRepository = organisedTourRepository;
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void autoDeleteOutdatedOrganisedTours() {
        List<OrganisedTour> allOrganisedTours = hikeRouteService.getAllOrganisedTours();
        LocalDateTime now = LocalDateTime.now();
        for (OrganisedTour tour : allOrganisedTours) {
            if (tour.getBeginningOfEvent().isBefore(now)) {
                hikeRouteService.deleteOutdatedOrganisedTourOf(tour.getId());
            }
        }
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
    public List<PictureURL> getImagesByHikeRouteId(@PathVariable(value = "hikeRouteId") Long hikeRouteId) {
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteId);
        return hikeRoute.map(HikeRoute::getPictureUrlList).orElse(null);
    }

    @PostMapping(value = "/OrganisedTour/create")
    public ResponseDTO createOrganisedRoute(@RequestBody @Valid OrganisedTourDTO organisedTourData,
                                            BindingResult bindingResult) {
        if (hikeRouteService.getHikeRouteOf(organisedTourData.getHikeRouteId()) == null) {
            bindingResult.addError(new FieldError("OrganisedTourDTO", "hikeRouteId", "No such tour in database!"));
        }
        if (organisedTourData.getBeginningOfEvent() != null &&
                hikeRouteService.DateTimeIsBeforeNow(organisedTourData.getBeginningOfEvent())) {
            bindingResult.addError(new FieldError("OrganisedTourDTO", "beginningOfEvent",
                    "Event date can not be in the Past!"));
        }
        if (bindingResult.hasErrors()) {
            return OrganisedTourErrorDTO.collectSpringErrorsFrom(bindingResult);
        } else {
            hikeRouteService.createOrganisedTour(organisedTourData);
            return new SuccessDTO();
        }
    }

    @GetMapping(value = "/OrganisedTour/{orgRouteId}")
    public OrganisedTourDTO getOrganisedRouteOf(@PathVariable(value = "orgRouteId") Long organisedRouteID) {
        return hikeRouteService.getOrganisedTourOf(organisedRouteID);
    }

    @GetMapping(value = "/OrganisedTour/getAll")
    public List<OrganisedTourDTO> getOrganisedRoutes() {
        return hikeRouteService.allOrganisedToursInDTO();
    }

    @PostMapping(value = "/OrganisedTour/addUserToMaybeList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseDTO addUserToOrganisedTourMaybeListOf (@RequestParam("tourID") Long organisedTourId ) {
        try {
            HikeMasterUser user = userService.getSignedInHikeMasterUser();
            OrganisedTour tour = organisedTourRepository.getOne(organisedTourId);
            hikeRouteService.addUserToMaybeListOf(tour, user);
            return new SuccessDTO();
        } catch (Exception e) {
            return new ErrorDTO(e.getMessage());
        }
    }

    //TODO: Need to implement remove user from lists feature
    //TODO: Should implement a modify organisedTour feature,  also need to have a get or modify DTO?!

    @PostMapping(value = "/OrganisedTour/addUserToWillBeList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseDTO addUserToOrganisedTourWillBeThereListOf (@RequestParam("tourID") Long organisedTourId ) {
        try {
            HikeMasterUser user = userService.getSignedInHikeMasterUser();
            OrganisedTour tour = organisedTourRepository.getOne(organisedTourId);
            hikeRouteService.addUserToWillBeListOf(tour, user);
            return new SuccessDTO();
        } catch (Exception e) {
            return new ErrorDTO(e.getMessage());
        }
    }

    @PostMapping(value = "/OrganisedTour/removeUserFromMaybeList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseDTO removeUserToOrganisedTourMaybeListOf (@RequestParam("tourID") Long organisedTourId ) {
        try {
            HikeMasterUser user = userService.getSignedInHikeMasterUser();
            OrganisedTour tour = organisedTourRepository.getOne(organisedTourId);
            hikeRouteService.removeUserFromMaybeListOf(tour, user);
            return new SuccessDTO();
        } catch (Exception e) {
            return new ErrorDTO(e.getMessage());
        }
    }

    @PostMapping(value = "/OrganisedTour/removeUserFromWillBeList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseDTO removeUserToOrganisedTourWillBeThereListOf (@RequestParam("tourID") Long organisedTourId ) {
        try {
            HikeMasterUser user = userService.getSignedInHikeMasterUser();
            OrganisedTour tour = organisedTourRepository.getOne(organisedTourId);
            hikeRouteService.removeUserFromWillBeListOf(tour, user);
            return new SuccessDTO();
        } catch (Exception e) {
            return new ErrorDTO(e.getMessage());
        }
    }

}
