package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class KMLfile {
    @Id
    @GeneratedValue
    @Column
    private Long kml_Id;
    
    @Column
    @Lob
    private String routeKMLInString;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private HikeRoute hikeRoute;

    public Long getKml_Id() {
        return kml_Id;
    }

    public void setKml_Id(Long kml_Id) {
        this.kml_Id = kml_Id;
    }

    public String getRouteKMLInString() {
        return routeKMLInString;
    }

    public void setRouteKMLInString(String routeKMLInString) {
        this.routeKMLInString = routeKMLInString;
    }

    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }
}
