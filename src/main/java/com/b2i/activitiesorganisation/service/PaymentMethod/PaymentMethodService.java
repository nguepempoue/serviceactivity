package com.b2i.activitiesorganisation.service.PaymentMethod;

import com.b2i.activitiesorganisation.dto.request.PaymentMethod.PaymentMethodRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentMethodService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createPaymentMethod(PaymentMethodRequest paymentMethodRequest);

    ResponseEntity<Object> findAllPaymentMethods();

    ResponseEntity<Object> updatePaymentMethod(Long idPaymentMethod, PaymentMethodRequest paymentMethodRequest);

    ResponseEntity<Object> deletePaymentMethod(Long idPaymentMethod);


    // MORE OPERATIONS //
    ResponseEntity<Object> findPaymentMethodById(Long idPaymentMethod);

    Long countAll();
}
