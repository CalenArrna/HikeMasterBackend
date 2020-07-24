package org.backend.Model;

import javax.persistence.*;

@Entity
public class Pictures {

    @Id
    @GeneratedValue
    private Long picturesId;
    @Column
    private Boolean isApprove;
    @ManyToOne
    private HikeRoute hikeRoute;

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
}
