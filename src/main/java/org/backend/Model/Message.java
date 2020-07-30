package org.backend.Model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Message {
    @Id
    @GeneratedValue
    private long massageId;
    @Column
    private String text;
    @ManyToOne
    private HikeMasterUser hikeMasterUser;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private HikeRoute hikeRoute;
    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime messageDate;


    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
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


