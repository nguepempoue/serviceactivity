package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.ProfitabilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitabilityTypeRepository extends JpaRepository<ProfitabilityType, Long> {
}
