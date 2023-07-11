package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.PenaltyType.PenaltyTypeRequest;
import com.b2i.activitiesorganisation.service.PenaltyType.PenaltyTypeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/penalty-types")
@CrossOrigin("*")
public class PenaltyTypeController {


    @Autowired
    private PenaltyTypeServiceImplementation penaltyServiceImplementation;


    // FIND ALL PENALTY TYPES
    @GetMapping
    public ResponseEntity<Object> findAllPenaltyTypes() {
        return penaltyServiceImplementation.findAllPenaltyTypes();
    }


    // UPDATE PENALTY TYPE
    @PutMapping("/{idPenaltyType}")
    public ResponseEntity<Object> updatePenaltyType(@PathVariable("idPenaltyType") Long idPenaltyType, @RequestBody PenaltyTypeRequest penaltyTypeRequest) {
        return penaltyServiceImplementation.updatePenaltyType(idPenaltyType, penaltyTypeRequest);
    }


    // DELETE PENALTY TYPE
    @DeleteMapping("/{idPenaltyType}")
    public ResponseEntity<Object> deletePenaltyType(@PathVariable("idPenaltyType") Long idPenaltyType) {
        return penaltyServiceImplementation.deletePenaltyType(idPenaltyType);
    }


    // FIND PENALTY TYPE BY ID
    @GetMapping("/{idPenaltyType}")
    public ResponseEntity<Object> findPenaltyTypeById(@PathVariable("idPenaltyType") Long idPenaltyType) {
        return penaltyServiceImplementation.findPenaltyTypeById(idPenaltyType);
    }
}
