package org.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column
    private String userName;
    @ManyToOne
    private HikeRoute hikeRoute;
    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime messageDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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



    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }
}


