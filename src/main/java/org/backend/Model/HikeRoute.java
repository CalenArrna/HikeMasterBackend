package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.javadoc.internal.doclets.toolkit.util.Utils;

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
    private  List<Messages>messages=new ArrayList<>();
    @Column
    private double rate;
    @Column
    private double coordinates;
    @Column
    private double tourLenght;
    @Column
    private int levelRise;
    @Column
    private int difficulty;
    @Column
    private double distanceFromLoc;
    @Column
    private int view;

    //"hikeID": "{hikeID}",
    //        "coordinate": “{coordinate}",
    //        "tour_lenght": "{tour_lenght}"
    //        "level_rise": "{level_rise}"
    //        "difficulity": "{difficulity}"
    //        "view": "{view}"
    //        "distance": "{distance}"


    public double getDistanceFromLoc() {
        return distanceFromLoc;
    }

    public void setDistanceFromLoc(double distanceFromLoc) {
        this.distanceFromLoc = distanceFromLoc;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

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

    public double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinates) {
        this.coordinates = coordinates;
    }
}


