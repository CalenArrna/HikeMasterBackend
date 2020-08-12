package org.backend.DTOs;

import org.backend.Model.HikeRoute;

public class WishRouteSuccessDTO extends ResponseDTO {
    private HikeRoute hikeRoute;

    public WishRouteSuccessDTO(HikeRoute hikeRoute) {
        this.hikeRoute = hikeRoute;
        success = true;
    }
}
