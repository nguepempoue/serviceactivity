package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.GainMode.GainModeRequest;
import com.b2i.activitiesorganisation.service.GainMode.GainModeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gain-modes")
@CrossOrigin("*")
public class GainModeController {


    @Autowired
    private GainModeServiceImplementation gainModeServiceImplementation;


    // CREATE GAIN MODE
    @PostMapping
    ResponseEntity<Object> createGainMode(@RequestBody GainModeRequest gainModeRequest) {
        return gainModeServiceImplementation.createGainMode(gainModeRequest);
    }


    // FIND ALL GAIN MODES
    @GetMapping
    ResponseEntity<Object> findAllGainModes() {
        return gainModeServiceImplementation.findAllGainModes();
    }


    // UPDATE GAIN MODE
    @PutMapping("/{idGainMode}")
    ResponseEntity<Object> updateGainMode(@PathVariable("idGainMode") Long idGainMode, @RequestBody GainModeRequest gainModeRequest) {
        return gainModeServiceImplementation.updateGainMode(idGainMode, gainModeRequest);
    }


    // DELETE GAIN MODE
    @DeleteMapping("/{idGainMode}")
    ResponseEntity<Object> deleteGainMode(@PathVariable("idGainMode") Long idGainMode) {
        return gainModeServiceImplementation.deleteGainMode(idGainMode);
    }


    // FIND GAIN MODE BY ID
    @GetMapping("/{idGainMode}")
    ResponseEntity<Object> findGainModeById(@PathVariable("idGainMode") Long idGainMode) {
        return gainModeServiceImplementation.findGainModeById(idGainMode);
    }

    @GetMapping("search")
    public ResponseEntity<Object> findGainModeByLabel(@RequestParam String label) {
        return gainModeServiceImplementation.findGainModeByLabel(label);
    }
}
