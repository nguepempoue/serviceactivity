package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.SubscriptionPayment.SubscriptionPaymentRequest;
import com.b2i.activitiesorganisation.service.SubscriptionPayment.SubscriptionPaymentServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription-payments")
@CrossOrigin("*")
public class SubscriptionPaymentController {

    private final SubscriptionPaymentServiceImplementation subscriptionPaymentServiceImplementation;

    public SubscriptionPaymentController(SubscriptionPaymentServiceImplementation subscriptionPaymentServiceImplementation) {
        this.subscriptionPaymentServiceImplementation = subscriptionPaymentServiceImplementation;
    }


    // FIND ALL PAYMENTS
    @GetMapping
    public ResponseEntity<Object> findAllSubscriptionsPayments() {
        return subscriptionPaymentServiceImplementation.findAllPayments();
    }


    // UPDATE PAYMENT
    @PutMapping("/{idSubscriptionPayment}")
    public ResponseEntity<Object> updateSubscriptionPayment(@PathVariable("idSubscriptionPayment") Long idSubscriptionPayment, @RequestBody SubscriptionPaymentRequest subscriptionPaymentRequest) {
        return subscriptionPaymentServiceImplementation.updatePayment(idSubscriptionPayment, subscriptionPaymentRequest);
    }


    // DELETE PAYMENT
    @DeleteMapping("/{idPayment}")
    public ResponseEntity<Object> deleteSubscriptionPayment(@PathVariable("idPayment") Long idPayment) {
        return subscriptionPaymentServiceImplementation.deletePayment(idPayment);
    }


    // FIND PAYMENT BY ID
    @GetMapping("/{idPayment}")
    public ResponseEntity<Object> findSubscriptionPaymentById(@PathVariable("idPayment") Long idPayment) {
        return subscriptionPaymentServiceImplementation.findPaymentById(idPayment);
    }
}
