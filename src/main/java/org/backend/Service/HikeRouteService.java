package org.backend.Service;

import org.apache.commons.lang3.tuple.Pair;
import org.backend.CoordinateDistanceCalculator.Haversine;
import org.backend.DTOs.HikeRouteSuccessDTO;
import org.backend.DTOs.MarkerDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.backend.DTOs.*;
import org.backend.Model.HikeMasterUser;
import org.backend.Model.HikeRoute;
import org.backend.Model.KMLfile;
import org.backend.Model.Pictures;
import org.backend.Model.QHikeRoute;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.ImageRepository;
import org.dozer.DozerBeanMapper;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: username to creation
//TODO: TimeStamp
//TODO: HIkreRoute ERrror messages
//TODO: Make database store Geometry types
//TODO: Repair database connection, now have to drop database...
//TODO: Insert End Point Elevation data!


@Service
public class HikeRouteService {
    @PersistenceContext
    EntityManager em;

    private DozerBeanMapper mapper;

    public HikeRouteService(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    HikeRouteRepository hikeRouteRepository;

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public HikeRoute getHikeRouteOf(long hikeRouteId) {
        List<HikeRoute> hikeRouteList = em.createQuery("select h from HikeRoute h where h.routeId = :hikeRouteId", HikeRoute.class)
                .setParameter("hikeRouteId", hikeRouteId)
                .getResultList();
        if (hikeRouteList.isEmpty()) {
            return null;
        } else {
            return hikeRouteList.get(0);
        }
    }

    @PersistenceContext
    EntityManager hikeRouteEntityManager;

    public List<HikeRoute> findHikeRoutesByParams(HikeRouteDTO hikeRouteDTO) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(hikeRouteEntityManager);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotBlank(hikeRouteDTO.getTourType())) {
            booleanBuilder.and(QHikeRoute.hikeRoute.tourType.like(hikeRouteDTO.getTourType()));
        }

        if (StringUtils.isNotBlank(hikeRouteDTO.getRouteType())) {
            booleanBuilder.and(QHikeRoute.hikeRoute.routeType.like(hikeRouteDTO.getRouteType()));
        }
        if (StringUtils.isNotBlank(hikeRouteDTO.getDifficulty())) {
            booleanBuilder.and(QHikeRoute.hikeRoute.difficulty.like(hikeRouteDTO.getDifficulty()));
        }
        if (hikeRouteDTO.getTourLength() != null) {
            booleanBuilder.and(QHikeRoute.hikeRoute.tourLength.loe(hikeRouteDTO.getTourLength()));
        }
        if (hikeRouteDTO.getLevelRise() != null) {
            booleanBuilder.and(QHikeRoute.hikeRoute.levelRise.loe(hikeRouteDTO.getLevelRise()));
        }
        if (hikeRouteDTO.getRate() != null) {
            booleanBuilder.and(QHikeRoute.hikeRoute.rate.loe((hikeRouteDTO.getRate())));
        }

        List<HikeRoute> routes = queryFactory.selectFrom(QHikeRoute.hikeRoute)
                .where(booleanBuilder)
                .fetch();

        if (routes == null) {
            return null;
        } else {
            return routes;
        }
    }

    @Transactional
    public void addKMLtoHikeRouteOf(Integer routeID, MultipartFile kml) throws XMLStreamException, IOException, ParseException {
        HikeRoute routeToUpdate = getHikeRouteOf(routeID);
        HikeRoute kmlDatas = getHikeRouteDataFrom(kml);

        KMLfile kmLfile = new KMLfile();
        kmLfile.setRouteKMLInString(new String(kml.getBytes()));

        routeToUpdate.setStartLat(kmlDatas.getStartLat());
        routeToUpdate.setStartLong(kmlDatas.getStartLong());
        routeToUpdate.setEndLat(kmlDatas.getEndLat());
        routeToUpdate.setEndLong(kmlDatas.getEndLong());
        routeToUpdate.setLevelRise(kmlDatas.getLevelRise());
        routeToUpdate.setTourLength(kmlDatas.getTourLength());
        routeToUpdate.setTourDate(kmlDatas.getTourDate());

        kmLfile.setHikeRoute(routeToUpdate);
        em.persist(kmLfile);
        em.persist(routeToUpdate);
    }

    @Transactional
    public Long addNewHikeRoute(HikeRouteDTO hikeRouteDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        HikeMasterUser hikeMasterUser = (HikeMasterUser) principal;
        HikeRoute hikeRoute = new HikeRoute();
        hikeRoute.setCreatedBy(hikeMasterUser.getUsername());
        hikeRoute.setRate(hikeRouteDTO.getRate());
        hikeRoute.setDifficulty(hikeRouteDTO.getDifficulty());
        hikeRoute.setTourType(hikeRouteDTO.getTourType());
        hikeRoute.setRouteType(hikeRouteDTO.getRouteType());
        hikeRoute.setText(hikeRouteDTO.getDescription());
        hikeRoute.setTitle(hikeRouteDTO.getTitle());
        em.persist(hikeRoute);
        return hikeRoute.getRouteId();
    }

    private HikeRoute getHikeRouteDataFrom(MultipartFile kml) throws XMLStreamException, ParseException {
        DateAndCoordinatesDTO coordinatesAndDate = parseKmlToListOfCoordinates(kml);
        HikeRoute newRoute = HikeRoute.createRouteFrom(coordinatesAndDate.getCoordinates());
        newRoute.setTourDate(coordinatesAndDate.getDateOfRoute());
        return newRoute;
    }

    private DateAndCoordinatesDTO parseKmlToListOfCoordinates(MultipartFile kml) throws XMLStreamException {
        List<Coordinate> listOfCoordinates = new ArrayList<>();
        LocalDateTime dateTimeOfRoute = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = inputFactory.createXMLEventReader(getValidFileStream(kml));
        while (eventReader.hasNext()) {
            XMLEvent nextEvent = eventReader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("TimeStamp")) {
                    nextEvent = eventReader.nextEvent();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    dateTimeOfRoute = LocalDateTime.parse(getValidDateTimeFrom(nextEvent), formatter);
                }
                if (startElement.getName().getLocalPart().equals("coord")) {
                    nextEvent = eventReader.nextEvent();
                    String coordinates = nextEvent.asCharacters().getData();
                    listOfCoordinates.add(getCoordinateFrom(coordinates));
                }
            }
        }
        return new DateAndCoordinatesDTO(listOfCoordinates, dateTimeOfRoute);
    }

    private String getValidDateTimeFrom(XMLEvent dateTagDataInKML) {
        String tryied = dateTagDataInKML.asCharacters().getData().split("\\.")[0];
        String[] splitted = tryied.split("T");
        return splitted[0] + " " + splitted[1];
    }

    private Coordinate getCoordinateFrom(String coordinates) {
        String[] arr = coordinates.split(" ");
        double x = Double.parseDouble(arr[1]);
        double y = Double.parseDouble(arr[0]);
        double z = Double.parseDouble(arr[2]);
        return new Coordinate(x, y, z);
    }


    private InputStream getValidFileStream(MultipartFile kml) { //TODO: do a valid exception/error handling!!!
        try {
            return kml.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<MarkerDTO> hikeRouteInArea(MarkerInputDTO areaToSearch) {
        return getFilteredList(areaToSearch);
    }

    private List<MarkerDTO> getFilteredList(MarkerInputDTO areaToSearch) {
        List<HikeRoute> all = getAllHikeRoute();
        List<MarkerDTO> filtered = new ArrayList<>();
        for (HikeRoute hikeRoute : all) {
            double distance = Haversine.distance(areaToSearch.getLatitude(), areaToSearch.getLongitude(),
                    hikeRoute.getStartLat(), hikeRoute.getStartLong());
            if (distance <= areaToSearch.getRadius()) {
                filtered.add(mapper.map(hikeRoute, MarkerDTO.class));
            }
        }
        return filtered;
    }

    public List<HikeRoute> getAllHikeRoute() {
        return em.createQuery("SELECT c FROM HikeRoute c").getResultList();
    }

    public HikeRoute updateHikeRoute(HikeRouteDTO hikeRouteDTO) {
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteDTO.getHikeRouteId());
        if (hikeRouteDTO.getTitle() != null) {
            hikeRoute.ifPresent(route -> route.setTitle(hikeRouteDTO.getTitle()));
        }
        if (hikeRouteDTO.getDescription() != null) {
            hikeRoute.ifPresent(route -> route.setText(hikeRouteDTO.getDescription()));
        }
        return hikeRoute.orElse(null);
    }

    public Pictures imageApproval(PictureDTO pictureDTO) {
        Optional<Pictures> pictures = imageRepository.findById(pictureDTO.getPictureId());
        pictures.ifPresent(value -> value.setApprove(pictureDTO.getApprove()));
        return pictures.orElse(null);
    }



    public String getKmlStringOf(Long id) {
        KMLfile kmLfile = (KMLfile) em.createQuery("select k from KMLfile k where k.hikeRoute.routeId = :routeID")
                .setParameter("routeID", id)
                .getSingleResult();
        return kmLfile.getRouteKMLInString();
    }
}
