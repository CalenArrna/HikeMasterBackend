package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrganisedTour {
    @GeneratedValue
    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String createdBy;

    @Column
    private LocalDateTime beginningOfEvent;

    @Column
    private long hikeRouteId;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    private List<HikeMasterUser> usersWillBeThere;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    private List<HikeMasterUser> usersMightBeThere;

    public OrganisedTour() {
    }

    public OrganisedTour(String name, String createdBy, long hikeRouteId, LocalDateTime dateOfTour) {
        this.name = name;
        this.createdBy = createdBy;
        this.beginningOfEvent = dateOfTour;
        this.hikeRouteId = hikeRouteId;
        usersMightBeThere = new ArrayList<>();
        usersWillBeThere = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getBeginningOfEvent() {
        return beginningOfEvent;
    }

    public void setBeginningOfEvent(LocalDateTime beginningOfEvent) {
        this.beginningOfEvent = beginningOfEvent;
    }

    public long getHikeRouteId() {
        return hikeRouteId;
    }

    public void setHikeRouteId(long hikeRouteId) {
        this.hikeRouteId = hikeRouteId;
    }

    public List<HikeMasterUser> getUsersWillBeThere() {
        return usersWillBeThere;
    }


    public List<HikeMasterUser> getUsersMightBeThere() {
        return usersMightBeThere;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void addUserToWillBeList (HikeMasterUser user) {
        usersWillBeThere.add(user);
    }

    public void addUserToMaybeList(HikeMasterUser user) {
        usersMightBeThere.add(user);
    }
}
