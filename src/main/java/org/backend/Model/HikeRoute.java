package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.backend.CoordinateDistanceCalculator.Haversine;
import org.backend.enums.RouteType;
import org.backend.enums.TourDifficulty;
import org.backend.enums.TourType;
import org.locationtech.jts.geom.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HikeRoute {
    @Id
    @GeneratedValue
    private long routeId;
    @Column
    private String title;
    @JsonIgnore
    @OneToMany
    private List<Messages> messages = new ArrayList<>();
    @Column
    private double rate;
    @Column
    private String createdBy;
    @Column
    private String routeKML;
    @Column
    private double startLat;
    @Column
    private double startLong;
    @Column
    private double endLat;
    @Column
    private double endLong;
    @Column
    private double tourLenght;
    @Column
    private int levelRise;
    @Column
    private TourDifficulty difficulty;
    @Column
    private double distanceFromLoc;
    @Column
    private RouteType routeType;
    @Column
    private TourType tourType;

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRouteKML() {
        return routeKML;
    }

    public void setRouteKML(String routeKML) {
        this.routeKML = routeKML;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLong() {
        return startLong;
    }

    public void setStartLong(double startLong) {
        this.startLong = startLong;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getEndLong() {
        return endLong;
    }

    public void setEndLong(double endLong) {
        this.endLong = endLong;
    }

    public double getTourLenght() {
        return tourLenght;
    }

    public void setTourLenght(double tourLenght) {
        this.tourLenght = tourLenght;
    }

    public int getLevelRise() {
        return levelRise;
    }

    public void setLevelRise(int levelRise) {
        this.levelRise = levelRise;
    }

    public TourDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TourDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public double getDistanceFromLoc() {
        return distanceFromLoc;
    }

    public void setDistanceFromLoc(double distanceFromLoc) {
        this.distanceFromLoc = distanceFromLoc;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public TourType getTourType() {
        return tourType;
    }

    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static HikeRoute createRouteFrom(List<Coordinate> list) {
        HikeRoute route = new HikeRoute();
        Coordinate start = list.get(0);
        Coordinate end = list.get(list.size() - 1);
        route.setEndLat(end.x);
        route.setEndLong(end.y);
        route.setStartLat(start.x);
        route.setStartLong(start.y);
        route.setTourLenght(calculateTourLength(list));
        route.setLevelRise(calculateElevation(list));
        return route;
    }

    public static int calculateElevation(List<Coordinate> list) {
        Coordinate max = list.stream().max((Coordinate c1, Coordinate c2) -> (int) (c1.getZ() - (c2.getZ()))).get();
        Coordinate min = list.stream().min((Coordinate c1, Coordinate c2) -> (int) (c1.getZ() - (c2.getZ()))).get();
        return (int) (max.getZ() - min.getZ());
    }

    public static double calculateTourLength(List<Coordinate> list) { // TODO: make it work with timeStamps
        List<Coordinate> shortenedList = shortenListToMinutes(list);
        double elevation = 0;
        Coordinate coordinate1 = null;
        for (Coordinate coordinate2 : shortenedList) {
            if (coordinate1 != null) {
                elevation += Haversine.distance(coordinate1.getX(), coordinate1.getY(),
                        coordinate2.getX(), coordinate2.getY());
            }
            coordinate1 = coordinate2;
        }
        return elevation;
    }

    private static List<Coordinate> shortenListToMinutes(List<Coordinate> list) {
        List<Coordinate> shortList = new ArrayList<>();
        int originalListSize = list.size();
        int iterationNumber = originalListSize / 60;
        int actIdx = 0;
        for (int i = 0; i < iterationNumber; i++) {
            shortList.add(list.get(actIdx));
            actIdx += 60;
        }
        if (list.size() % 60 > 0) {
            shortList.add(list.get(originalListSize - 1));
        }
        return shortList;
    }
}


