package com.b2i.activitiesorganisation.service.Payment;

import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentService {


    // CRUD OPERATIONS //
    ResponseEntity<Object> createPayment(String token, Long idSession, Long idUser, Long idPaymentMethod, PaymentRequest paymentRequest);

    ResponseEntity<Object> findAllPayments();

    ResponseEntity<Object> updatePayment(Long idPayment, PaymentRequest paymentRequest);

    ResponseEntity<Object> deletePayment(Long idPayment);


    // MORE OPERATIONS //
    ResponseEntity<Object> findPaymentById(Long idPayment);

    ResponseEntity<Object> findAllPaymentsOfASession(Long idSession);

    ResponseEntity<Object> checkPayment(Long idPayment);

    ResponseEntity<Object> validatePayment(Long idPayment);

    ResponseEntity<Object> findAllPaymentByUserAndSession(Long idUser, Long idSession);

    Long countAllPayments();
}
