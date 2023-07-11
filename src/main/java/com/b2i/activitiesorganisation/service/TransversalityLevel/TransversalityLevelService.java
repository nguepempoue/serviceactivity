package com.b2i.activitiesorganisation.service.TransversalityLevel;

import com.b2i.activitiesorganisation.dto.request.TransversalityLevel.TransversalityLevelRequest;
import org.springframework.http.ResponseEntity;

public interface TransversalityLevelService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createLevel(TransversalityLevelRequest transversalityLevelRequest);

    ResponseEntity<Object> findAllLevels();

    ResponseEntity<Object> updateLevel(Long id, TransversalityLevelRequest transversalityLevelRequest);

    ResponseEntity<Object> deleteLevel(Long id);

    // MORE //
    Long countAllLevels();

    ResponseEntity<Object> findById(Long id);

    ResponseEntity<Object> findTransversalityLevelByLabel(String label);
}
