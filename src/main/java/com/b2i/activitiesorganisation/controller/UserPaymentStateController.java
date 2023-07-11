package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.service.UserPaymentState.UserPaymentStateServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-states")
@CrossOrigin("*")
public class UserPaymentStateController {

    @Autowired
    private UserPaymentStateServiceImplementation implementation;


    // FIND ALL USER PAYMENT STATES
    @GetMapping
    public ResponseEntity<Object> findAllUserPaymentStates() {
        return implementation.findAllUserPaymentStates();
    }


    // GET USER PAYMENT STATE BY ID
    @GetMapping("/{idUserPaymentState}")
    public ResponseEntity<Object> findUserPaymentStateById(@PathVariable("idUserPaymentState") Long idUserPaymentState) {
        return implementation.findUserPaymentStateById(idUserPaymentState);
    }
}
