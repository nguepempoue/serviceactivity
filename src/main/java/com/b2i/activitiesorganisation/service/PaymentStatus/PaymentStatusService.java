package com.b2i.activitiesorganisation.service.PaymentStatus;

import com.b2i.activitiesorganisation.dto.request.PaymentStatus.PaymentStatusRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentStatusService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createPaymentStatus(PaymentStatusRequest paymentStatusRequest);

    ResponseEntity<Object> findAllPaymentStatus();

    ResponseEntity<Object> updatePaymentStatus(Long idPaymentStatus, PaymentStatusRequest paymentStatusRequest);

    ResponseEntity<Object> deletePaymentStatus(Long idPaymentStatus);


    // MORE OPERATIONS
    ResponseEntity<Object> findPaymentStatusById(Long idPaymentStatus);

    Long countAll();
}
