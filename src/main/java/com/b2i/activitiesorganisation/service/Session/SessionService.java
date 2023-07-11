package com.b2i.activitiesorganisation.service.Session;

import com.b2i.activitiesorganisation.dto.request.Session.SessionRequest;
import org.springframework.http.ResponseEntity;

public interface SessionService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createSession(Long idCycle, SessionRequest sessionRequest);

    ResponseEntity<Object> findAllSessions();

    ResponseEntity<Object> updateSession(Long id, SessionRequest sessionRequest);

    ResponseEntity<Object> deleteSessionOfACycle(Long idSession, Long idCycle);


    // MORE OPERATIONS //
    ResponseEntity<Object> findSessionById(Long id);

    ResponseEntity<Object> updateContributionDeadline(Long idSession, SessionRequest sessionRequest);

    ResponseEntity<Object> openSessionById(Long idSession);

    ResponseEntity<Object> closeSessionById(Long idSession);

    ResponseEntity<Object> generateWinnerOfASession(String token, Long idSession);

    Long countAllSessions();
}
