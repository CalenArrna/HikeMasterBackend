package org.backend.Repository;

import org.backend.Model.HikeRoute;
import org.backend.Model.KMLfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KMLRepository extends JpaRepository<KMLfile,Long> {
       @Transactional
       public List<KMLfile> removeKMLfileByHikeRoute(HikeRoute hikeRoute);
}
