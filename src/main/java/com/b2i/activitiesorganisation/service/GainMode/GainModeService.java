package com.b2i.activitiesorganisation.service.GainMode;

import com.b2i.activitiesorganisation.dto.request.GainMode.GainModeRequest;
import org.springframework.http.ResponseEntity;

public interface GainModeService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createGainMode(GainModeRequest gainModeRequest);

    ResponseEntity<Object> findAllGainModes();

    ResponseEntity<Object> updateGainMode(Long idGainMode, GainModeRequest gainModeRequest);

    ResponseEntity<Object> deleteGainMode(Long idGainMode);


    // MORE OPERATIONS //
    ResponseEntity<Object> findGainModeById(Long idGainMode);

    ResponseEntity<Object> findGainModeByLabel(String label);

    Long countAllGainModes();
}
