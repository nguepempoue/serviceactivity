package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.service.UserPenaltyState.UserPenaltyStateServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/penalty-states")
@CrossOrigin("*")
public class UserPenaltyStateController {

    @Autowired
    private UserPenaltyStateServiceImplementation implementation;


    // FIND ALL PENALTY STATES
    @GetMapping
    public ResponseEntity<Object> findAllUserPenaltyState() {
        return implementation.findAllUserPenaltyState();
    }


    // FIND PENALTY STATE BY ID
    @GetMapping("/{idUserPenaltyState}")
    public ResponseEntity<Object> findUserPenaltyStateById(@PathVariable("idUserPenaltyState") Long idUserPenaltyState) {
        return implementation.findUserPenaltyStateById(idUserPenaltyState);
    }

}
