package org.backend.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.backend.CoordinateDistanceCalculator.Haversine;
import org.backend.DTOs.HikeRouteDTO;
import org.backend.DTOs.MarkerDTO;
import org.backend.Model.HikeRoute;
import org.backend.Model.QHikeRoute;
import org.backend.Repository.HikeRouteRepository;
import org.dozer.DozerBeanMapper;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public HikeRoute hikeRouteDetails(long hikeRouteId) {
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
    public void createNewHikeRouteFrom(MultipartFile kml) throws XMLStreamException {
        HikeRoute newHikeRoute = createHikeRouteObject(kml);
        em.persist(newHikeRoute);
    }

    @Transactional
    public Long addNewHikeRoute(HikeRouteDTO hikeRouteDTO) {
        HikeRoute hikeRoute = new HikeRoute();
        hikeRoute.setRate(hikeRouteDTO.getRate());
        hikeRoute.setDifficulty(hikeRouteDTO.getDifficulty());
        hikeRoute.setTourType(hikeRouteDTO.getTourType());
        hikeRoute.setRouteType(hikeRouteDTO.getRouteType());
        hikeRoute.setText(hikeRouteDTO.getDescription());
        hikeRoute.setTitle(hikeRouteDTO.getTitle());
        em.persist(hikeRoute);
        return hikeRoute.getRouteId();

    }
        private HikeRoute createHikeRouteObject (MultipartFile kml) throws XMLStreamException {
            List<Coordinate> coordinates = parseKmlToListOfCoordinates(kml);
            HikeRoute route = HikeRoute.createRouteFrom(coordinates);
            route.setRouteKML(kml.toString());
            return route;
        }

        private List<Coordinate> parseKmlToListOfCoordinates (MultipartFile kml) throws XMLStreamException {
            List<Coordinate> listOfCoordinates = new ArrayList<>();
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(getValidFileStream(kml));
            while (eventReader.hasNext()) {
                XMLEvent nextEvent = eventReader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("coord")) {
                        nextEvent = eventReader.nextEvent();
                        String coordinates = nextEvent.asCharacters().getData();
                        listOfCoordinates.add(getCoordinateFrom(coordinates));
                    }
                }
            }
            return listOfCoordinates;
        }

        private Coordinate getCoordinateFrom (String coordinates){
            String[] arr = coordinates.split(" ");
            double x = Double.parseDouble(arr[0]);
            double y = Double.parseDouble(arr[1]);
            double z = Double.parseDouble(arr[2]);
            return new Coordinate(x, y, z);
        }


        private InputStream getValidFileStream (MultipartFile kml){ //TODO: do a valid exception/error handling!!!
            try {
                return kml.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        public List<MarkerDTO> hikeRouteInArea ( double latitude, double longitude, int radius){
            return getFilteredList(latitude, longitude, radius);
        }

        private List<MarkerDTO> getFilteredList ( double latitude, double longitude, int radius){
            List<HikeRoute> all = hikeRouteRepository.findAll();
            List<MarkerDTO> filtered = new ArrayList<>();
            for (HikeRoute hikeRoute : all) {
                double distance = Haversine.distance(latitude, longitude,
                        hikeRoute.getStartLat(), hikeRoute.getStartLong());
                if (distance <= radius) {
                    filtered.add(mapper.map(hikeRoute, MarkerDTO.class));
                }
            }
            return filtered;
        }


    }