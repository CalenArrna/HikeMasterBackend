package org.backend.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.backend.Model.HikeRoute;
import org.backend.Model.QHikeRoute;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class HikeRouteService {
     @PersistenceContext
    EntityManager hikeRouteEntityManager;
    public List<HikeRoute> findHikeRoutesByParams( double length,int level,String difficultly,double distance,int spectacle){
        JPAQueryFactory queryFactory = new JPAQueryFactory(hikeRouteEntityManager);
        QHikeRoute hikeRoute= QHikeRoute.hikeRoute;
        BooleanBuilder booleanBuilder=new BooleanBuilder();
        if(length!=0){
            booleanBuilder.and(hikeRoute.tourLenght.between(0,length));
        }
        if (level!=0){
            booleanBuilder.and(hikeRoute.levelRise.eq(level));
        }
        if(distance!=0){
            booleanBuilder.and(hikeRoute.distanceFromLoc.eq(distance));
        }
        if(StringUtils.isNotBlank(difficultly)){
            booleanBuilder.and(hikeRoute.difficulty.like(difficultly));
        }
        if(spectacle!=0){
            booleanBuilder.and(hikeRoute.view.eq(spectacle));
        }
       return queryFactory.selectFrom(hikeRoute)
               .where(booleanBuilder)
               .fetch();
    }

}
