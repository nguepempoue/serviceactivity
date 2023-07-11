package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.service.Refund.RefundServiceImplementattion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refund")
@CrossOrigin("*")
public class RefundController {

    private final RefundServiceImplementattion refundServiceImplementattion;

    public RefundController(RefundServiceImplementattion refundServiceImplementattion) {
        this.refundServiceImplementattion = refundServiceImplementattion;
    }

    // FIND BY ID
    @GetMapping("/{idRefund}")
    public ResponseEntity<Object> findRefundById(@PathVariable("idRefund") Long idRefund) {
        return refundServiceImplementattion.findRefundById(idRefund);
    }
}
