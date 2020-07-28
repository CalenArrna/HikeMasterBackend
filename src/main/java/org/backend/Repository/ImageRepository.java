package org.backend.Repository;

import org.backend.Model.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository< Pictures,Long> {
    Optional<Pictures> findByName(String name);
}
