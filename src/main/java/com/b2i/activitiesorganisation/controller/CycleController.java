package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Cycle.CycleRequest;
import com.b2i.activitiesorganisation.service.Cycle.CycleServiceImplementation;
import com.b2i.activitiesorganisation.service.Session.SessionServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/cycles")
public class CycleController {


    @Autowired
    private CycleServiceImplementation cycleServiceImplementation;

    @Autowired
    private SessionServiceImplementation sessionServiceImplementation;


    // FIND ALL CYCLES
    @GetMapping
    public ResponseEntity<Object> findAllCycles() {
        return cycleServiceImplementation.findAllCycles();
    }


    // UPDATE CYCLE
    @PutMapping("/{idCycle}")
    public ResponseEntity<Object> updateCycle(@PathVariable("idCycle") Long idCycle, @RequestBody CycleRequest cycleRequest) {
        return cycleServiceImplementation.updateCycle(cycleRequest, idCycle);
    }


    // FIND CYCLE BY ID
    @GetMapping("/{idCycle}")
    public ResponseEntity<Object> findCycleById(@PathVariable("idCycle") Long idCycle) {
        return cycleServiceImplementation.findCycleById(idCycle);
    }


    // FIND ALL SESSION OF A CYCLE
    @GetMapping("/{idCycle}/sessions")
    public ResponseEntity<Object> findAllSessionsOfCycle(@PathVariable("idCycle") Long idCycle) {
        return cycleServiceImplementation.findAllSessionsOfCycle(idCycle);
    }


    // DELETE SESSION OF A CYCLE
    @DeleteMapping("/{idCycle}/sessions/{idSession}")
    public ResponseEntity<Object> deleteSessionOfACycle(@PathVariable("idSession") Long idSession, @PathVariable("idCycle") Long idCycle) {
        return sessionServiceImplementation.deleteSessionOfACycle(idSession, idCycle);
    }


    // CLOSE CYCLE
    @PatchMapping("/{idCycle}/close")
    public ResponseEntity<Object> closeCycleById(@PathVariable("idCycle") Long idCycle) {
        return cycleServiceImplementation.closeCycleById(idCycle);
    }

    @GetMapping("search")
    public ResponseEntity<Object> findCycleByName(@RequestParam String name) {
        return cycleServiceImplementation.findCycleByName(name);
    }
}
