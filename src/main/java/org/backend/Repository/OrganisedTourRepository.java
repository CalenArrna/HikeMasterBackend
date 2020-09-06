package org.backend.Repository;

import org.backend.Model.OrganisedTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("OrganisedTourRepository")
public interface OrganisedTourRepository extends JpaRepository<OrganisedTour, Long> {
}
