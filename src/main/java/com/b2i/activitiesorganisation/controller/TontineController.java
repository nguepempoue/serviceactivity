package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Cycle.CycleRequest;
import com.b2i.activitiesorganisation.dto.request.Tontine.TontineRequest;
import com.b2i.activitiesorganisation.service.Cycle.CycleServiceImplementation;
import com.b2i.activitiesorganisation.service.Tontine.TontineServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tontines")
public class TontineController {


    @Autowired
    private TontineServiceImplementation tontineServiceImplementation;

    @Autowired
    private CycleServiceImplementation cycleServiceImplementation;


    // CREATE TONTINE
    @PostMapping("/club/{idClub}/level/{idLevel}/contributionFrequency/{idContributionFrequency}/sessionFrequency/{idSessionFrequency}/gain-mode/{idGainMode}")
    public ResponseEntity<Object> createNewTontine(@RequestParam("token") String token, @RequestBody TontineRequest tontineRequest, @PathVariable("idClub") Long idClub, @PathVariable("idLevel") Long idTransversalityLevel, @PathVariable("idContributionFrequency") Long idContributionFrequency, @PathVariable("idSessionFrequency") Long idTontineSessionFrequency, @PathVariable("idGainMode") Long idGainMode) {
        return tontineServiceImplementation.createTontine(token, tontineRequest, idClub, idTransversalityLevel, idContributionFrequency, idTontineSessionFrequency, idGainMode);
    }


    // FIND ALL TONTINES
    @GetMapping
    public ResponseEntity<Object> findAllTontines() {
        return tontineServiceImplementation.findAllTontines();
    }

    @GetMapping("search")
    public ResponseEntity<Object> findTontineByName(@RequestParam String name) {
        return tontineServiceImplementation.findTontineByName(name);
    }

    // UPDATE TONTINE
    @PutMapping("/{idTontine}")
    public ResponseEntity<Object> updateTontine(@RequestBody TontineRequest tontineRequest, @PathVariable("idTontine") Long idTontine) {
        return tontineServiceImplementation.updateTontine(tontineRequest, idTontine);
    }


    // GET TONTINE BY ID
    @GetMapping("/{idTontine}")
    public ResponseEntity<Object> findTontineById(@PathVariable("idTontine") Long idTontine) {
        return tontineServiceImplementation.findTontineById(idTontine);
    }


    // DELETE TONTINE BY ID
    @DeleteMapping("/{idTontine}")
    public ResponseEntity<Object> deleteTontineById(@RequestParam("token") String token, @PathVariable("idTontine") Long idTontine) {
        return tontineServiceImplementation.deleteTontineById(token, idTontine);
    }


    // SET FREQUENCY FOR TONTINE
    @PatchMapping("/{idTontine}/set-frequency/{idFrequency}")
    public ResponseEntity<Object> setFrequency(@PathVariable("idTontine") Long idTontine, @PathVariable("idFrequency") Long idFrequency) {
        return tontineServiceImplementation.setFrequency(idTontine, idFrequency);
    }


    // SET TRANSVERSALITY LEVEL
    @PatchMapping("/{idTontine}/set-transversality-level/{idLevel}")
    public ResponseEntity<Object> setLevel(@PathVariable("idTontine") Long idTontine, @PathVariable("idLevel") Long idLevel) {
        return tontineServiceImplementation.setTransversalLevel(idTontine, idLevel);
    }


    // ADD PARTICIPANT
    @PatchMapping("/{idTontine}/add-participant/{idUser}/plan/{planValue}")
    public ResponseEntity<Object> addParticipant(@RequestParam("token") String token, @PathVariable("idTontine") Long idTontine, @PathVariable("idUser") Long idUser, @PathVariable("planValue") Long planValue)
    {
        return tontineServiceImplementation.addParticipant(token, idTontine, idUser, planValue);
    }


    // REMOVE PARTICIPANT
    @PatchMapping("/{idTontine}/remove-participant/{idUser}")
    public ResponseEntity<Object> removeParticipant(@PathVariable("idTontine") Long idTontine, @PathVariable("idUser") Long idUser) {
        return tontineServiceImplementation.removeParticipant(idTontine, idUser);
    }


    // CREATE CYCLE FOR TONTINE
    @PostMapping("/{idTontine}/cycles")
    public ResponseEntity<Object> createCycleForTontine(@PathVariable("idTontine") Long idTontine, @RequestBody CycleRequest cycleRequest) {
        return cycleServiceImplementation.createCycle(cycleRequest, idTontine);
    }


    // DELETE CYCLE FROM A TONTINE
    @DeleteMapping("/{idTontine}/cycles/{idCycle}")
    public ResponseEntity<Object> deleteCycleFromTontine(@PathVariable("idTontine") Long idTontine, @PathVariable("idCycle") Long idCycle) {
        return cycleServiceImplementation.deleteCycleById(idCycle, idTontine);
    }


    // SET GAIN MODE FOR TONTINE
    @PatchMapping("/{idTontine}/set-gain-mode/{idGainMode}")
    public ResponseEntity<Object> setGainMode(@PathVariable("idTontine") Long idTontine, @PathVariable("idGainMode") Long idGainMode) {
        return tontineServiceImplementation.setGainMode(idTontine, idGainMode);
    }


    // GET TONTINE USERS
    @GetMapping("/{idTontine}/users")
    public ResponseEntity<Object> getTontineUsers(@PathVariable("idTontine") Long idTontine) {
        return tontineServiceImplementation.getTontineUsers(idTontine);
    }


    // GET TONTINE CYCLES
    @GetMapping("/{idTontine}/cycles")
    public ResponseEntity<Object> findAllCyclesOfTontine(@PathVariable("idTontine") Long idTontine) {
        return tontineServiceImplementation.findAllCyclesOfTontine(idTontine);
    }


    // SET STATUS
    @PatchMapping("/{idTontine}/status/{idStatus}")
    public ResponseEntity<Object> setStatus(@PathVariable("idTontine") Long idTontine, @PathVariable("idStatus") Long idStatus) {
        return tontineServiceImplementation.setStatus(idTontine, idStatus);
    }
}
