package org.backend.Model;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private long massageId;
    @Column
    private String text;
    @ManyToOne
    private HikeMasterUser hikeMasterUser;
    @ManyToOne
    private HikeRoute hikeRoute;
    @Column
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMassageId() {
        return massageId;
    }

    public void setMassageId(long massageId) {
        this.massageId = massageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HikeMasterUser getHikeMasterUser() {
        return hikeMasterUser;
    }

    public void setHikeMasterUser(HikeMasterUser hikeMasterUser) {
        this.hikeMasterUser = hikeMasterUser;
    }

    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }
}
