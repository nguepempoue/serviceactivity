package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
