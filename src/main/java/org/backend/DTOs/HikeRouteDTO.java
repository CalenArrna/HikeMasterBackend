package org.backend.DTOs;


import org.springframework.web.multipart.MultipartFile;



public class HikeRouteDTO {

    private Long hikeRouteId;
    private String tourType;
    private String routeType;
    private String difficulty;
    private Integer tourLength;
    private Integer levelRise;
    private Integer rate;
    private String title;
    private  String description;
    private MultipartFile kml;
    private String createdBy;


    public MultipartFile getKml() {
        return kml;
    }

    public void setKml(MultipartFile kml) {
        this.kml = kml;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Long getHikeRouteId() {
        return hikeRouteId;
    }

    public void setHikeRouteId(Long hikeRouteId) {
        this.hikeRouteId = hikeRouteId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
