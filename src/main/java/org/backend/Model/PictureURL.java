package org.backend.Model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.net.URL;
@Component
@Entity
public class PictureURL {
    @Id
    @GeneratedValue
    private Long urlId;
    @Column
    private URL pictureUrl;
    @ManyToOne
    private HikeRoute hikeRoute;

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public URL getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(URL pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }
}
