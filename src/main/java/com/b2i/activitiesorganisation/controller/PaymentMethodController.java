package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.PaymentMethod.PaymentMethodRequest;
import com.b2i.activitiesorganisation.service.PaymentMethod.PaymentMethodServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-methods")
@CrossOrigin("*")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodServiceImplementation paymentMethodServiceImplementation;


    // CREATE
    @PostMapping
    public ResponseEntity<Object> createPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest) {
        return paymentMethodServiceImplementation.createPaymentMethod(paymentMethodRequest);
    }


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAllPaymentMethods() {
        return paymentMethodServiceImplementation.findAllPaymentMethods();
    }


    // UPDATE
    @PutMapping("/{idPaymentMethod}")
    public ResponseEntity<Object> updatePaymentMethod(@PathVariable("idPaymentMethod") Long idPaymentMethod, @RequestBody PaymentMethodRequest paymentMethodRequest) {
        return paymentMethodServiceImplementation.updatePaymentMethod(idPaymentMethod, paymentMethodRequest);
    }


    // DELETE
    @DeleteMapping("/{idPaymentMethod}")
    public ResponseEntity<Object> deletePaymentMethod(@PathVariable("idPaymentMethod") Long idPaymentMethod) {
        return paymentMethodServiceImplementation.deletePaymentMethod(idPaymentMethod);
    }


    // FIND BY ID
    @GetMapping("/{idPaymentMethod}")
    public ResponseEntity<Object> findPaymentMethodById(@PathVariable("idPaymentMethod") Long idPaymentMethod) {
        return paymentMethodServiceImplementation.findPaymentMethodById(idPaymentMethod);
    }
}
