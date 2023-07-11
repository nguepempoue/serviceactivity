package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.TransversalityLevel.TransversalityLevelRequest;
import com.b2i.activitiesorganisation.service.TransversalityLevel.TransversalityLevelServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/transversality-levels")
public class TransversalityLevelController {

    @Autowired
    private TransversalityLevelServiceImplementation implementation;


    // CREATE
    @PostMapping
    public ResponseEntity<Object> createLevel(@RequestBody TransversalityLevelRequest request) {
        return implementation.createLevel(request);
    }


    // READ
    @GetMapping
    public ResponseEntity<Object> findAllLevels() {
        return implementation.findAllLevels();
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLevel(@PathVariable("id") Long id, @RequestBody TransversalityLevelRequest request) {
        return implementation.updateLevel(id, request);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLevel(@PathVariable("id") Long id) {
        return implementation.deleteLevel(id);
    }


    // FIND LEVEL BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> findLevelById(@PathVariable("id") Long id) {
        return implementation.findById(id);
    }

    @GetMapping("search")
    public ResponseEntity<Object> findTransversalityLevelByLabel(@RequestParam String label) {
        return implementation.findTransversalityLevelByLabel(label);
    }
}
