package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.RefundAmount.RefundAmountRequest;
import com.b2i.activitiesorganisation.service.RefundAmount.RefundAmountServiceImplementation;
import com.b2i.activitiesorganisation.service.SecurityDeposit.SecurityDepositServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security-deposits")
@CrossOrigin("*")
public class SecurityDepositController {

    @Autowired
    private SecurityDepositServiceImplementation securityDepositServiceImplementation;

    @Autowired
    private RefundAmountServiceImplementation refundAmountServiceImplementation;


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAll() {
        return securityDepositServiceImplementation.findAll();
    }


    // FIND BY ID
    @GetMapping("/{idDeposit}")
    public ResponseEntity<Object> findSecurityDepositById(@PathVariable("idDeposit") Long idDeposit) {
        return securityDepositServiceImplementation.findSecurityDepositById(idDeposit);
    }

    // REFUND AMOUNT
    @PatchMapping("/refund-amount/{idDeposit}/idPaymentMethod/{idPaymentMethod}")
    public ResponseEntity<Object> refundAmountSecutityDeposit(@PathVariable("idDeposit") Long idDeposit, @PathVariable("idPaymentMethod") Long idPaymentMethod, @RequestParam String token, @RequestBody RefundAmountRequest refundAmountRequest) {
        return refundAmountServiceImplementation.refundAmountSecutityDeposit(token, idDeposit, idPaymentMethod,  refundAmountRequest);
    }


}
