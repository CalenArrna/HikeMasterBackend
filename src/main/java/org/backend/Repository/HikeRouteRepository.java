package org.backend.Repository;

import org.backend.Model.HikeRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("HikeRouteRepository")
public interface HikeRouteRepository extends JpaRepository<HikeRoute, Long> {

}
