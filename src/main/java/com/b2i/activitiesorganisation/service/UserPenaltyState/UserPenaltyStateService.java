package com.b2i.activitiesorganisation.service.UserPenaltyState;

import org.springframework.http.ResponseEntity;

public interface UserPenaltyStateService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> findAllUserPenaltyState();


    // MORE OPERATIONS //
    ResponseEntity<Object> findUserPenaltyStateById(Long idUserPenaltyState);

    ResponseEntity<Object> findUserPenaltyStateByUserIdAndSession(Long idUser, Long idSession);

    ResponseEntity<Object> findAllUserPenaltyStateBySession(Long idSession);
}
