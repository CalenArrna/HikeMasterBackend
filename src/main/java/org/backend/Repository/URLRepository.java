package org.backend.Repository;

import org.backend.Model.PictureURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository< PictureURL,Long> {
}
