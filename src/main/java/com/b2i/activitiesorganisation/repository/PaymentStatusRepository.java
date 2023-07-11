package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    Optional<PaymentStatus> findPaymentStatusByLabel(String label);
}
