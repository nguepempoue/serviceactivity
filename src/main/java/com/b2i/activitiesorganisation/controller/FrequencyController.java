package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Frequency.FrequencyRequest;
import com.b2i.activitiesorganisation.service.Frequency.FrequencyServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/frequencies")
public class FrequencyController {


    @Autowired
    private FrequencyServiceImplementation frequencyServiceImplementation;


    // CREATE
    @PostMapping
    public ResponseEntity<Object> createFrequency(@RequestBody FrequencyRequest frequencyRequest) {
        return frequencyServiceImplementation.createFrequency(frequencyRequest);
    }


    // READ
    @GetMapping
    public ResponseEntity<Object> findAllFrequencies() {
        return frequencyServiceImplementation.findAllFrequencies();
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFrequency(@PathVariable("id") Long id, @RequestBody FrequencyRequest frequencyRequest) {
        return frequencyServiceImplementation.updateFrequency(id, frequencyRequest);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFrequency(@PathVariable("id") Long id) {
        return frequencyServiceImplementation.deleteFrequency(id);
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getFrequencyById(@PathVariable("id") Long id) {
        return frequencyServiceImplementation.getFrequencyById(id);
    }

    @GetMapping("search")
    public ResponseEntity<Object> findFrequencyByLabel(@RequestParam String label) {
        return frequencyServiceImplementation.findFrequencyByLabel(label);
    }
}
