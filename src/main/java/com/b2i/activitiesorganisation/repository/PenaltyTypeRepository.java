package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.PenaltyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyTypeRepository extends JpaRepository<PenaltyType, Long> {
}
