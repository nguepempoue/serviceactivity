package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.RiskProfile.RiskProfileRequest;
import com.b2i.activitiesorganisation.service.RiskProfile.RiskProfileServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk-profiles")
@CrossOrigin("*")
public class RiskProfileController {

    @Autowired
    private RiskProfileServiceImplementation riskProfileServiceImplementation;


    // CREATE RISK PROFILE
    @PostMapping
    public ResponseEntity<Object> createRiskProfile(@RequestBody RiskProfileRequest riskProfileRequest) {
        return riskProfileServiceImplementation.createRiskProfile(riskProfileRequest);
    }


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAllRiskProfiles() {
        return riskProfileServiceImplementation.findAllRiskProfiles();
    }


    // UPDATE
    @PutMapping("/{idProfile}")
    public ResponseEntity<Object> updateRiskProfile(@PathVariable("idProfile") Long idProfile, @RequestBody RiskProfileRequest riskProfileRequest) {
        return riskProfileServiceImplementation.updateRiskProfile(idProfile, riskProfileRequest);
    }


    // DELETE
    @DeleteMapping("/{idProfile}")
    public ResponseEntity<Object> deleteRiskProfile(@PathVariable("idProfile") Long idProfile) {
        return riskProfileServiceImplementation.deleteRiskProfile(idProfile);
    }


    // FIND BY ID
    @GetMapping("/{idProfile}")
    public ResponseEntity<Object> findRiskProfileById(@PathVariable("idProfile") Long idProfile) {
        return riskProfileServiceImplementation.findRiskProfileById(idProfile);
    }
}
