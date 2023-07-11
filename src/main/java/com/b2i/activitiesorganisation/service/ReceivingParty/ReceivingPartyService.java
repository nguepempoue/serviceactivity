package com.b2i.activitiesorganisation.service.ReceivingParty;

import com.b2i.activitiesorganisation.dto.request.ReceivingParty.ReceivingPartyRequest;
import com.b2i.activitiesorganisation.model.ReceivingParty;
import org.springframework.http.ResponseEntity;

public interface ReceivingPartyService {

    // CRUD OPERATIONS //
    ReceivingParty createReceivingParty(ReceivingPartyRequest receivingPartyRequest);

    ResponseEntity<Object> findAllReceivingParties();

    ResponseEntity<Object> updateReceivingParty(Long idReceivingParty, ReceivingPartyRequest receivingPartyRequest);

    ResponseEntity<Object> deleteReceivingParty(Long idReceivingParty);


    // MORE OPERATIONS //
    ResponseEntity<Object> findReceivingPartyById(Long idReceivingParty);

    Long countAll();
}
