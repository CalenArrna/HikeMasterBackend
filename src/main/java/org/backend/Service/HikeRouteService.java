package org.backend.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.backend.Model.HikeRoute;
import org.backend.Model.QHikeRoute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class HikeRouteService  {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public HikeRoute hikeRouteDetails(long hikeRouteId) {
        List<HikeRoute> hikeRouteList = em.createQuery("select h from HikeRoute h where h.routeId = :hikeRouteId", HikeRoute.class)
                .setParameter("hikeRouteId", hikeRouteId)
                .getResultList();
        if (hikeRouteList.isEmpty()){
            return null;
        }
        else{
            return hikeRouteList.get(0);
        }
    }

    @PersistenceContext
    EntityManager hikeRouteEntityManager;

    public List<HikeRoute> findHikeRoutesByParams(String tourType, String routeType, String difficultly, Integer length, Integer levelRise, Integer rate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(hikeRouteEntityManager);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(tourType)){
            booleanBuilder.and(QHikeRoute.hikeRoute.tourType.like(tourType));
        }

        if(StringUtils.isNotBlank(routeType)) {
            booleanBuilder.and(QHikeRoute.hikeRoute.routeType.like(routeType));
        }
        if(StringUtils.isNotBlank(difficultly)){
            booleanBuilder.and(QHikeRoute.hikeRoute.difficulty.like(difficultly));
        }
        if(rate!=null){
            booleanBuilder.and(QHikeRoute.hikeRoute.rate.loe(rate));
        }
        if(length!=null){
            booleanBuilder.and(QHikeRoute.hikeRoute.tourLength.loe(length));
        }
        if (levelRise!=null){
            booleanBuilder.and(QHikeRoute.hikeRoute.levelRise.loe(levelRise));
        }


        List<HikeRoute> routes = queryFactory.selectFrom(QHikeRoute.hikeRoute)
                .where(booleanBuilder)
                .fetch();

        if (routes==null){
            return null;
        }else {
            return routes;
        }


    }




}
