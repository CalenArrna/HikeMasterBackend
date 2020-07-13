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
    private User user;
    @ManyToOne
    private HikeRoute hikeRoute;
}
