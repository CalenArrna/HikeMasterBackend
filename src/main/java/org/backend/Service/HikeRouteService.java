package org.backend.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.backend.DTOs.HikeRouteDTO;
import org.backend.Model.HikeRoute;
import org.backend.Model.QHikeRoute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class HikeRouteService {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public HikeRoute hikeRouteDetails(long hikeRouteId) {
        List<HikeRoute> hikeRouteList = em.createQuery("select h from HikeRoute h where h.routeId = :hikeRouteId", HikeRoute.class)
                .setParameter("hikeRouteId", hikeRouteId)
                .getResultList();
        if (hikeRouteList.isEmpty()) {
            return null;
        } else {
            return hikeRouteList.get(0);
        }
    }

    @PersistenceContext
    EntityManager hikeRouteEntityManager;

    public List<HikeRoute> findHikeRoutesByParams(HikeRouteDTO hikeRouteDTO) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(hikeRouteEntityManager);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotBlank(hikeRouteDTO.getTourType())) {
            booleanBuilder.and(QHikeRoute.hikeRoute.tourType.like(hikeRouteDTO.getTourType()));
        }

        if (StringUtils.isNotBlank(hikeRouteDTO.getRouteType())) {
            booleanBuilder.and(QHikeRoute.hikeRoute.routeType.like(hikeRouteDTO.getRouteType()));
        }
        if (StringUtils.isNotBlank(hikeRouteDTO.getDifficulty())) {
            booleanBuilder.and(QHikeRoute.hikeRoute.difficulty.like(hikeRouteDTO.getDifficulty()));
        }
        if (hikeRouteDTO.getTourLength() != null) {
            booleanBuilder.and(QHikeRoute.hikeRoute.tourLength.loe(hikeRouteDTO.getTourLength()));
        }
        if (hikeRouteDTO.getLevelRise() != null) {
            booleanBuilder.and(QHikeRoute.hikeRoute.levelRise.loe(hikeRouteDTO.getLevelRise()));
        }
        if (hikeRouteDTO.getRate() != null) {
            booleanBuilder.and(QHikeRoute.hikeRoute.rate.loe((hikeRouteDTO.getRate())));
        }


        List<HikeRoute> routes = queryFactory.selectFrom(QHikeRoute.hikeRoute)
                .where(booleanBuilder)
                .fetch();

        if (routes == null) {
            return null;
        } else {
            return routes;
        }


    }
    @Transactional
    public Long addNewHikeRoute(HikeRouteDTO hikeRouteDTO){
        HikeRoute hikeRoute=new HikeRoute();
        hikeRoute.setRate(hikeRouteDTO.getRate());
        hikeRoute.setDifficulty(hikeRouteDTO.getDifficulty());
        hikeRoute.setTourType(hikeRouteDTO.getTourType());
        hikeRoute.setRouteType(hikeRouteDTO.getRouteType());
        hikeRoute.setText(hikeRouteDTO.getDescription());
        hikeRoute.setTitle(hikeRouteDTO.getTitle());
        em.persist(hikeRoute);
        return hikeRoute.getRouteId();

    }


}
