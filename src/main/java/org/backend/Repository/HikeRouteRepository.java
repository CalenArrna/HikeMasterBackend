package org.backend.Repository;

import org.backend.Model.HikeRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("HikeRouteRepository")
public interface HikeRouteRepository extends JpaRepository<HikeRoute,Long> {

    @Query("select h from HikeRoute h where h.tourType= :tourtype AND h.routeType= :routeType AND h.difficulty= :difficultly " +
            "and h.tourLength= :tourLenght AND h.levelRise= :levelRise AND h.rate= :rate ")
   List <HikeRoute> findByParams(@Param("tourtype") String tour_type,
                                 @Param("routeType") String route_type,
                                 @Param("difficultly") String difficultly,
                                 @Param("tourLenght") Integer tourLength,
                                 @Param("levelRise") Integer levelRise,
                                 @Param("rate") Integer rate);
}
