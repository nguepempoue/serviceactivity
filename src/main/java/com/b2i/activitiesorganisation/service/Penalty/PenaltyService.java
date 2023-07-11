package com.b2i.activitiesorganisation.service.Penalty;

import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import com.b2i.activitiesorganisation.dto.request.Penalty.PenaltyRequest;
import org.springframework.http.ResponseEntity;

public interface PenaltyService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createPenaltyForUserAndSession(String token, Long idUser, Long idSession, Long idPenaltyType, PenaltyRequest penaltyRequest);
    ResponseEntity<Object> findAllPenalties();

    ResponseEntity<Object> updatePenalty(Long idPenalty, PenaltyRequest penaltyRequest);

    ResponseEntity<Object> deletePenalty(Long idPenalty);


    // MORE OPERATIONS //
    ResponseEntity<Object> findPenaltyById(Long idPenalty);

    ResponseEntity<Object> payPenalty(String token, Long idPenalty, Long idUser, Long idPaymentMethod, PaymentRequest paymentRequest);

    ResponseEntity<Object> findPenaltiesOfASession(Long idSession);

    ResponseEntity<Object> findAllPenaltiesByUserAndSession(Long idUser, Long idSession);

    Long countAllPenalties();
}
