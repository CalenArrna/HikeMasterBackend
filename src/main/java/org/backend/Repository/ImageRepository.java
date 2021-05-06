package org.backend.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.backend.Model.HikeRoute;
import org.backend.Model.Pictures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository< Pictures,Long> {

    Optional<Pictures> findByName(String name);
   @Transactional
    public List<Pictures> removePicturesByHikeRoute(HikeRoute hikeRoute);

}

