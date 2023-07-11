package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.UserPenaltyState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPenaltyStateRepository extends JpaRepository<UserPenaltyState, Long> {
}
