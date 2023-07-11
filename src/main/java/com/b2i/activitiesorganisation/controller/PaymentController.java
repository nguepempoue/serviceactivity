package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import com.b2i.activitiesorganisation.service.Payment.PaymentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payments")
@RestController
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentServiceImplementation paymentServiceImplementation;


    // FIND ALL PAYMENTS
    @GetMapping
    public ResponseEntity<Object> findAllPayments() {
        return paymentServiceImplementation.findAllPayments();
    }


    // UPDATE PAYMENT
    @PutMapping("/{idPayment}")
    public ResponseEntity<Object> updatePayment(@PathVariable("idPayment") Long idPayment, @RequestBody PaymentRequest paymentRequest) {
        return paymentServiceImplementation.updatePayment(idPayment, paymentRequest);
    }


    // DELETE PAYMENT
    @DeleteMapping("/{idPayment}")
    public ResponseEntity<Object> deletePayment(@PathVariable("idPayment") Long idPayment) {
        return paymentServiceImplementation.deletePayment(idPayment);
    }


    // FIND PAYMENT BY ID
    @GetMapping("/{idPayment}")
    public ResponseEntity<Object> findPaymentById(@PathVariable("idPayment") Long idPayment) {
        return paymentServiceImplementation.findPaymentById(idPayment);
    }


    // CHECK PAYMENT
    @PatchMapping("/{idPayment}/check-payment")
    public ResponseEntity<Object> checkPayment(@PathVariable("idPayment") Long idPayment) {
        return paymentServiceImplementation.checkPayment(idPayment);
    }


    // VALIDATE PAYMENT
    @PatchMapping("/{idPayment}/validate-payment")
    public ResponseEntity<Object> validatePayment(@PathVariable("idPayment") Long idPayment) {
        return paymentServiceImplementation.validatePayment(idPayment);
    }
}
