package org.backend.Model;

import javax.persistence.*;

@Entity
public class Pictures {



    @Id
    @GeneratedValue
    private Long picturesId;
    @Column
    private Boolean isApprove=false;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Column(name = "picByte", length = 16_000_000)
    @Lob
    private byte[] picByte;
    @ManyToOne
    private HikeRoute hikeRoute;

    public Pictures(String name, String type, byte[] picByte) {
       this.name=name;
       this.type=type;
       this.picByte=picByte;
    }

    public Pictures() {
    }

    public Long getPicturesId() {
        return picturesId;
    }

    public void setPicturesId(Long picturesId) {
        this.picturesId = picturesId;
    }

    public Boolean getApprove() {
        return isApprove;
    }

    public void setApprove(Boolean approve) {
        isApprove = approve;
    }

    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}
