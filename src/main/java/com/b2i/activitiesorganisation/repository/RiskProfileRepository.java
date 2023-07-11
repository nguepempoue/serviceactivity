package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.RiskProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskProfileRepository extends JpaRepository<RiskProfile, Long> {

}
