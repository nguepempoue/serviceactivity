package com.b2i.activitiesorganisation.controller;


import com.b2i.activitiesorganisation.service.RefundAmount.RefundAmountServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refund-amount")
@CrossOrigin("*")
public class refundAmountController {

    private final RefundAmountServiceImplementation refundAmountServiceImplementation;

    public refundAmountController(RefundAmountServiceImplementation refundAmountServiceImplementation) {
        this.refundAmountServiceImplementation = refundAmountServiceImplementation;
    }

    // FIND BY ID
    @GetMapping("/{idRefundAmount}")
    public ResponseEntity<Object> findRefundAmountById(@PathVariable("idRefundAmount") Long idRefundAmount) {
        return refundAmountServiceImplementation.findRefundAmountById(idRefundAmount);
    }
}
