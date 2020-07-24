package org.backend.DTOs;

public class HikeRouteDTO {
    private String tourType;
    private String routeType;
    private String difficulty;
    private Integer tourLength;
    private Integer levelRise;
    private Integer rate;

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficultly) {
        this.difficulty = difficultly;
    }

    public Integer getTourLength() {
        return tourLength;
    }

    public void setTourLength(Integer tourLength) {
        this.tourLength = tourLength;
    }

    public Integer getLevelRise() {
        return levelRise;
    }

    public void setLevelRise(Integer levelRise) {
        this.levelRise = levelRise;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
