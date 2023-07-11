package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import com.b2i.activitiesorganisation.service.Penalty.PenaltyServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/penalties")
@CrossOrigin("*")
public class PenaltyController {

    @Autowired
    private PenaltyServiceImplementation penaltyServiceImplementation;


    // FIND ALL PENALTIES
    @GetMapping
    public ResponseEntity<Object> findAllPenalties() {
        return penaltyServiceImplementation.findAllPenalties();
    }


    // FIND PENALTY BY ID
    @GetMapping("/{idPenalty}")
    public ResponseEntity<Object> findPenaltyById(@PathVariable("idPenalty") Long idPenalty) {
        return penaltyServiceImplementation.findPenaltyById(idPenalty);
    }


    // PAY PENALTY
    @PatchMapping("/{idPenalty}/user/{idUser}/pay/payment-method/{idPaymentMethod}")
    public ResponseEntity<Object> payPenalty(@RequestParam("token") String token, @PathVariable("idPenalty") Long idPenalty, @PathVariable("idUser") Long idUser, @PathVariable("idPaymentMethod") Long idPaymentMethod, @RequestBody PaymentRequest paymentRequest) {
        return penaltyServiceImplementation.payPenalty(token, idPenalty, idUser, idPaymentMethod, paymentRequest);
    }
}
