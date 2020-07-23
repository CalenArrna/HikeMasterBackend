package org.backend.DTOs;

import org.backend.Model.HikeRoute;

import java.util.ArrayList;
import java.util.List;

public class HikeRouteSuccessDTO extends ResponseDTO {

    private List<HikeRoute> hikeRoutes ;
    private HikeRoute hikeRoute;

    public HikeRouteSuccessDTO() {
        success=true;
    }

    public List<HikeRoute> getHikeRoutes() {
        return hikeRoutes;
    }

    public void setHikeRoutes(List<HikeRoute> hikeRoutes) {
        this.hikeRoutes = hikeRoutes;
    }

    public HikeRoute getHikeRoute() {
        return hikeRoute;
    }

    public void setHikeRoute(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
    }
}
