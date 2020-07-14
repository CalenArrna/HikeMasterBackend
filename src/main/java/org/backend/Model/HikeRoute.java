package org.backend.Model;
import javax.persistence.*;

@Entity
public class HikeRoute {
    @Id
    @GeneratedValue
    private long routeId;


    @Column
    private double coordinates;
}
