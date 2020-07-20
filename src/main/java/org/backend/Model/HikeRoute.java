package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer rate;
    @Column
    private Integer coordinates;
    @Column
    private int tourLength;
    @Column
    private int levelRise;
    @Column
    private String difficulty;
    @Column
    private double distanceFromLoc;
    @Column
    private String routeType;
    @Column
    private String tourType;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public double getDistanceFromLoc() {
        return distanceFromLoc;
    }

    public void setDistanceFromLoc(double distanceFromLoc) {
        this.distanceFromLoc = distanceFromLoc;
    }

    public int getTourLength() {
        return tourLength;
    }

    public void setTourLength(int tourLength) {
        this.tourLength = tourLength;
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


    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setCoordinates(Integer coordinates) {
        this.coordinates = coordinates;
    }
}


