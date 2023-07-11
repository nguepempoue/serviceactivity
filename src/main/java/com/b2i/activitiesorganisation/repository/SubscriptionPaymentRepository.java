package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.SubscriptionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long> {
}
