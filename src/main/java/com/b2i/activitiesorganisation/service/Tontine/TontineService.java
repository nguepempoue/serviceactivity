package com.b2i.activitiesorganisation.service.Tontine;

import com.b2i.activitiesorganisation.dto.request.Tontine.TontineRequest;
import com.b2i.activitiesorganisation.model.Tontine;
import org.springframework.http.ResponseEntity;

public interface TontineService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createTontine(String token, TontineRequest tontineRequest, Long idClub, Long idTransversalityLevel, Long idContributionFrequency, Long idTontineSessionFrequency, Long idGainMode);

    ResponseEntity<Object> findAllTontines();

    ResponseEntity<Object> updateTontine(TontineRequest tontineRequest, Long idTontine);

    ResponseEntity<Object> deleteTontineById(String token, Long idTontine);


    // MORE //
    ResponseEntity<Object> findTontineById(Long id);

    ResponseEntity<Object> setFrequency(Long idTontine, Long idFrequency);

    ResponseEntity<Object> setTransversalLevel(Long idTontine, Long idLevel);

    ResponseEntity<Object> addParticipant(String token, Long idTontine, Long idUser, Long plan);

    ResponseEntity<Object> removeParticipant(Long idTontine, Long idUser);

    ResponseEntity<Object> setGainMode(Long idTontine, Long idGainMode);

    ResponseEntity<Object> getTontineUsers(Long idTontine);

    ResponseEntity<Object> findAllCyclesOfTontine(Long idTontine);

    ResponseEntity<Object> setStatus(Long idTontine, Long idStatus);

    Tontine getTontineOfASession(Long idSession);

    ResponseEntity<Object> findTontineByName(String name);


}
