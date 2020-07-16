package org.backend.Service;

import org.backend.Model.HikeRoute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Collectors;

@Service
public class HikeRouteService {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public HikeRoute hikeRouteDetails(long hikeRouteId){
        return em.createQuery("select h from HikeRoute h", HikeRoute.class).getResultList()
                .stream()
                .filter(hikeRoute -> hikeRoute.getRouteId() == hikeRouteId).collect(Collectors.toList()).get(0);
    }

}
