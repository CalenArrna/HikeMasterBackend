package org.backend.DTOs;

import org.backend.Model.HikeRoute;


import java.util.List;

public class HikeRouteSuccessDTO extends ResponseDTO {
    private  HikeRoute hikeRoute;
    private List<HikeRoute> hikeRoutes;


    public HikeRouteSuccessDTO(Long hikeRouteId) {
       id=hikeRouteId;
       success = true;
    }

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
