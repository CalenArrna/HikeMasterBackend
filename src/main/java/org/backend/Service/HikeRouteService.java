package org.backend.Service;


import org.backend.Model.HikeRoute;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
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
import java.util.stream.Collectors;


@Service
public class HikeRouteService {
    private MultipartFile fileToConvert = null;
    @PersistenceContext
    EntityManager em;

    @Transactional
    public HikeRoute hikeRouteDetails(long hikeRouteId) {
        return em.createQuery("select h from HikeRoute h where h.routeId = :hikeRouteId", HikeRoute.class)
                .setParameter("hikeRouteId", hikeRouteId)
                .getSingleResult();
    }

    @Transactional
    public void createNewHikeRouteFrom(MultipartFile kml) throws XMLStreamException {
        fileToConvert = kml; //TODO: get it without field
        LineString lineString = getLineStringOfFile();
        em.createNativeQuery("INSERT INTO hike_route (route_line) VALUE (?)")
                .setParameter(1,lineString)
                .executeUpdate();
    }
    
    private HikeRoute createHikeRouteObject (LineString lineString) {
        HikeRoute route = new HikeRoute();
        route.setRouteLine(lineString);
        return route;
    }

    private LineString getLineStringOfFile() throws XMLStreamException {
        GeometryFactory factory = new GeometryFactory();
        Coordinate[] routeCoordinates = getCoordinatesFromKml();
        return factory.createLineString(routeCoordinates);
    }

    private Coordinate[] getCoordinatesFromKml() throws XMLStreamException {
        List<Coordinate> list = parseKmlToListOfCoordinates();
        Coordinate[] coordinates = new Coordinate[1];
        coordinates = list.toArray(coordinates);
        return coordinates;
    }

    private List<Coordinate> parseKmlToListOfCoordinates () throws XMLStreamException {
        List<Coordinate> listOfCoordinates = new ArrayList<>();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = inputFactory.createXMLEventReader(getValidFileStream());
        while (eventReader.hasNext()){
            XMLEvent nextEvent = eventReader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("coord")){
                    nextEvent = eventReader.nextEvent();
                    String coordinates = nextEvent.asCharacters().getData(); //TODO : How to get data from there? This point kills
                    listOfCoordinates.add(getCoordinateFrom(coordinates));
                }
            }
        }
        return listOfCoordinates;
    }
    
    private Coordinate getCoordinateFrom (String coordinates) {
        String[] arr = coordinates.split(" ");
        double x = Double.parseDouble( arr[0]);
        double y = Double.parseDouble( arr[1]);
        double z = Double.parseDouble( arr[2]);
        return new Coordinate(x,y,z);
    }
    //TODO: Repair databaseconnection, now have to drop database...

    private InputStream getValidFileStream() {
        try {
            InputStream stream = fileToConvert.getInputStream();
            return stream;
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO: do a valid exception/error handling!!!
        }
    }
}
