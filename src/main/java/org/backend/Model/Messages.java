package org.backend.Model;

import javax.persistence.*;

@Entity
public class Messages {
    @Id
    @GeneratedValue
    private long massageId;
    @Column
    private String text;
    @ManyToOne
    private SecUser user;
    @ManyToOne
    private HikeRoute hikeRoute;

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

    public SecUser getUser() {
        return user;
    }

    public void setUser(SecUser user) {
        this.user = user;
    }

    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }
}

