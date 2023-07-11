package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.PaymentStatus.PaymentStatusRequest;
import com.b2i.activitiesorganisation.service.PaymentStatus.PaymentStatusServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-status")
@CrossOrigin("*")
public class PaymentStatusController {

    @Autowired
    private PaymentStatusServiceImplementation paymentStatusServiceImplementation;


    // CREATE PAYMENT STATUS
    @PostMapping
    public ResponseEntity<Object> createPaymentStatus(@RequestBody PaymentStatusRequest paymentStatusRequest){
        return paymentStatusServiceImplementation.createPaymentStatus(paymentStatusRequest);
    }


    // FIND ALL PAYMENT STATUS
    @GetMapping
    public ResponseEntity<Object> findAllPaymentStatus() {
        return paymentStatusServiceImplementation.findAllPaymentStatus();
    }


    // UPDATE PAYMENT STATUS
    @PutMapping("/{idPaymentStatus}")
    public ResponseEntity<Object> updatePaymentStatus(@PathVariable("idPaymentStatus") Long idPaymentStatus, @RequestBody PaymentStatusRequest paymentStatusRequest) {
        return paymentStatusServiceImplementation.updatePaymentStatus(idPaymentStatus, paymentStatusRequest);
    }


    // DELETE PAYMENT STATUS
    @DeleteMapping("/{idPaymentStatus}")
    public ResponseEntity<Object> deletePaymentStatus(@PathVariable("idPaymentStatus") Long idPaymentStatus) {
        return paymentStatusServiceImplementation.deletePaymentStatus(idPaymentStatus);
    }


    // FIND PAYMENT STATUS BY ID
    @GetMapping("/{idPaymentStatus}")
    public ResponseEntity<Object> findPaymentStatusById(@PathVariable("idPaymentStatus") Long idPaymentStatus) {
        return paymentStatusServiceImplementation.findPaymentStatusById(idPaymentStatus);
    }

}
