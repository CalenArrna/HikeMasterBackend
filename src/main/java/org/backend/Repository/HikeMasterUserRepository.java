package org.backend.Repository;

import org.backend.Model.HikeMasterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("HikeMasterUserRepository")
public interface HikeMasterUserRepository extends JpaRepository<HikeMasterUser, Long> {
}
