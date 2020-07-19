package org.backend.Service;

import org.backend.Model.HikeRoute;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
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
        return em.createQuery("select h from HikeRoute h where h.routeId = :hikeRouteId", HikeRoute.class)
                .setParameter("hikeRouteId", hikeRouteId)
                .getSingleResult();
    }

    @PersistenceContext
    EntityManager hikeRouteEntityManager;
    public List<HikeRoute> findHikeRoutesByParams(String tourType, String routeType, String difficultly, int length, int levelRise, Double rate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(hikeRouteEntityManager);
        QHikeRoute hikeRoute=QHikeRoute.hikeRoute;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(tourType)){
            booleanBuilder.and(hikeRoute.tourType.like(tourType));
        }
        if(StringUtils.isNotBlank(routeType)) {
            booleanBuilder.and(hikeRoute.routeType.like(routeType));
        }
        if(StringUtils.isNotBlank(difficultly)){
            booleanBuilder.and(hikeRoute.difficulty.like(difficultly));
        }
        if(length!=0){
            booleanBuilder.and(hikeRoute.tourLenght.between(0,length));
        }
        if (levelRise!=0){
            booleanBuilder.and(hikeRoute.levelRise.between(0,levelRise));
        }
        if(rate!=0){
            booleanBuilder.and(hikeRoute.rate.eq(rate));
        }

       return queryFactory.selectFrom(hikeRoute)
               .where(booleanBuilder)
               .fetch();
    }



    }
