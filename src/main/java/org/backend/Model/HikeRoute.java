package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.backend.enums.RouteType;
import org.backend.enums.TourDifficulty;
import org.backend.enums.TourType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HikeRoute {
    @Id
    @GeneratedValue
    private long routeId;
    @JsonIgnore
    @OneToMany
    private  List<Message>messages=new ArrayList<>();
    @Column
    private double rate;
    @Column
    private double coordinates;
    @Column
    private double tourLenght;
    @Column
    private int levelRise;
    @Column
    private TourDifficulty difficulty;
    @Column
    private RouteType routeType;
    @Column
    private TourType tourType;

    public TourDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TourDifficulty difficulty) {
        this.difficulty = difficulty;
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


    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinates) {
        this.coordinates = coordinates;
    }
}


