package org.backend.DTOs;


public class HikeRouteErrorDTO extends ResponseDTO{

   private String[] routeId;
   private String[] messages;
   private String[] rate;
   private String[] coordinates;
   private String[] tourLength;
   private String[] levelRise;
   private String[] difficulty;
   private String[] distanceFromLoc;
   private String[] routeType;
   private String[] tourType;

    public HikeRouteErrorDTO() {

        success=false;
    }

    public String[] getRouteId() {
        return routeId;
    }

    public void setRouteId(String[] routeId) {
        this.routeId = routeId;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    public String[] getRate() {
        return rate;
    }

    public void setRate(String[] rate) {
        this.rate = rate;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    public String[] getTourLength() {
        return tourLength;
    }

    public void setTourLength(String[] tourLength) {
        this.tourLength = tourLength;
    }

    public String[] getLevelRise() {
        return levelRise;
    }

    public void setLevelRise(String[] levelRise) {
        this.levelRise = levelRise;
    }

    public String[] getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String[] difficulty) {
        this.difficulty = difficulty;
    }

    public String[] getDistanceFromLoc() {
        return distanceFromLoc;
    }

    public void setDistanceFromLoc(String[] distanceFromLoc) {
        this.distanceFromLoc = distanceFromLoc;
    }

    public String[] getRouteType() {
        return routeType;
    }

    public void setRouteType(String[] routeType) {
        this.routeType = routeType;
    }

    public String[] getTourType() {
        return tourType;
    }

    public void setTourType(String[] tourType) {
        this.tourType = tourType;
    }
}
