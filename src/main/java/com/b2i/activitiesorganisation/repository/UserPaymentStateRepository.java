package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.UserPaymentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentStateRepository extends JpaRepository<UserPaymentState, Long> {
}
