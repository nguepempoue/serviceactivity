package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.ReceivingParty.ReceivingPartyRequest;
import com.b2i.activitiesorganisation.service.ReceivingParty.ReceivingPartyServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receiving-parties")
@CrossOrigin("*")
public class ReceivingPartyController {

    @Autowired
    private ReceivingPartyServiceImplementation receivingPartyServiceImplementation;


    // FIND
    @GetMapping
    public ResponseEntity<Object> findAllReceivingParties() {
        return receivingPartyServiceImplementation.findAllReceivingParties();
    }


    // UPDATE
    @PutMapping("/{idReceivingParty}")
    public ResponseEntity<Object> updateReceivingParty(@PathVariable("idReceivingParty") Long idReceivingParty, @RequestBody ReceivingPartyRequest receivingPartyRequest) {
        return receivingPartyServiceImplementation.updateReceivingParty(idReceivingParty, receivingPartyRequest);
    }


    // DELETE
    @DeleteMapping("/{idReceivingParty}")
    public ResponseEntity<Object> deleteReceivingParty(Long idReceivingParty) {
        return receivingPartyServiceImplementation.deleteReceivingParty(idReceivingParty);
    }


    // FIND BY ID
    @GetMapping("/{idReceivingParty}")
    public ResponseEntity<Object> findReceivingPartyById(@PathVariable("idReceivingParty") Long idReceivingParty) {
        return receivingPartyServiceImplementation.findReceivingPartyById(idReceivingParty);
    }

}
