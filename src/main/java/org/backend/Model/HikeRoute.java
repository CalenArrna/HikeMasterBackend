package org.backend.Model;

import jdk.javadoc.internal.doclets.toolkit.util.Utils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HikeRoute {
    @Id
    @GeneratedValue
    private long routeId;
    @OneToMany
    private  List<Messages>messages=new ArrayList<>();
    @Column
    private int rate;
    @Column
    private double coordinates;
}
