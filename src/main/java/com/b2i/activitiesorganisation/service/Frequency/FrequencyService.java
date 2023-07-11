package com.b2i.activitiesorganisation.service.Frequency;

import com.b2i.activitiesorganisation.dto.request.Frequency.FrequencyRequest;
import org.springframework.http.ResponseEntity;

public interface FrequencyService {

    // CRUD OPERATIONS
    ResponseEntity<Object> createFrequency(FrequencyRequest frequencyRequest);

    ResponseEntity<Object> findAllFrequencies();

    ResponseEntity<Object> updateFrequency(Long id, FrequencyRequest frequencyRequest);

    ResponseEntity<Object> deleteFrequency(Long id);


    // MORE
    ResponseEntity<Object> getFrequencyById(Long id);

    ResponseEntity<Object> findFrequencyByLabel(String label);

    Long countAll();
}
