package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByUserId(Long userId);
}
