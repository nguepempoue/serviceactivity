package com.b2i.activitiesorganisation.service.UserPaymentState;

import org.springframework.http.ResponseEntity;

public interface UserPaymentStateService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> findAllUserPaymentStates();


    // MORE OPERATIONS //
    ResponseEntity<Object> findUserPaymentStateById(Long idUserPaymentState);

    ResponseEntity<Object> findUserPaymentStateByUserIdAndSession(Long idSession, Long userId);

    ResponseEntity<Object> findAllUserPaymentStateBySession(Long idSession);
}
