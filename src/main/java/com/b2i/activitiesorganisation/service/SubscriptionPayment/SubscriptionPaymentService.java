package com.b2i.activitiesorganisation.service.SubscriptionPayment;

import com.b2i.activitiesorganisation.dto.request.SubscriptionPayment.SubscriptionPaymentRequest;
import org.springframework.http.ResponseEntity;

public interface SubscriptionPaymentService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createPayment(String token, Long idSubscription, Long idPaymentMethod, SubscriptionPaymentRequest subscriptionPaymentRequest);

    ResponseEntity<Object> findAllPayments();

    ResponseEntity<Object> updatePayment(Long idPayment, SubscriptionPaymentRequest subscriptionPaymentRequest);

    ResponseEntity<Object> deletePayment(Long idPayment);

    ResponseEntity<Object> findPaymentById(Long idPayment);
}
