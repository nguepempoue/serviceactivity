package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.RefundType.RefundTypeRequest;
import com.b2i.activitiesorganisation.service.RefundType.RefundTypeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refund-type")
@CrossOrigin("*")
public class RefundTypeController {

    @Autowired
    private RefundTypeServiceImplementation refundTypeServiceImplementation;


    // CREATE
    @PostMapping
    public ResponseEntity<Object> createRefundType(@RequestBody RefundTypeRequest refundTypeRequest) {
        return refundTypeServiceImplementation.createRefundType(refundTypeRequest);
    }


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAllRefundTypes() {
        return refundTypeServiceImplementation.findAllRefundTypes();
    }


    // UPDATE
    @PutMapping("/{idType}")
    public ResponseEntity<Object> updateRefundType(@PathVariable("idType") Long idType, @RequestBody RefundTypeRequest refundTypeRequest) {
        return refundTypeServiceImplementation.updateRefundType(idType, refundTypeRequest);
    }


    // DELETE
    @DeleteMapping("/{idType}")
    public ResponseEntity<Object> deleteRefundType(@PathVariable("idType") Long idType) {
        return refundTypeServiceImplementation.deleteRefundType(idType);
    }


    // FIND BY ID
    @GetMapping("/{idType}")
    public ResponseEntity<Object> findRefundTypeById(@PathVariable("idType") Long idType) {
        return refundTypeServiceImplementation.findRefundTypeById(idType);
    }
}
