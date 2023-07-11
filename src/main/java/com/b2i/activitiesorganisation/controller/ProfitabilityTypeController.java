package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.ProfitabilityType.ProfitabilityTypeRequest;
import com.b2i.activitiesorganisation.service.ProfitabilityType.ProfitabilityTypeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profitability-types")
@CrossOrigin("*")
public class ProfitabilityTypeController {

    @Autowired
    private ProfitabilityTypeServiceImplementation profitabilityTypeServiceImplementation;


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAllProfitabilityTypes() {
        return profitabilityTypeServiceImplementation.findAllProfitabilityTypes();
    }


    // UPDATE
    @PutMapping("/{idType}")
    public ResponseEntity<Object> updateProfitabilityType(@PathVariable("idType") Long idType, @RequestBody ProfitabilityTypeRequest profitabilityTypeRequest) {
        return profitabilityTypeServiceImplementation.updateProfitabilityType(idType, profitabilityTypeRequest);
    }


    // DELETE
    @DeleteMapping("/{idType}")
    public ResponseEntity<Object> deleteProfitabilityType(@PathVariable("idType") Long idType) {
        return profitabilityTypeServiceImplementation.deleteProfitabilityType(idType);
    }


    // FIND BY ID
    @GetMapping("/{idType}")
    public ResponseEntity<Object> findProfitabilityTypeById(@PathVariable("idType") Long idType) {
        return profitabilityTypeServiceImplementation.findProfitabilityTypeById(idType);
    }
}
