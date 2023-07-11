package com.b2i.activitiesorganisation.service.Cycle;

import com.b2i.activitiesorganisation.dto.request.Cycle.CycleRequest;
import org.springframework.http.ResponseEntity;

public interface CycleService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createCycle(CycleRequest cycleRequest, Long idTontine);

    ResponseEntity<Object> findAllCycles();

    ResponseEntity<Object> updateCycle(CycleRequest cycleRequest, Long idCycle);

    ResponseEntity<Object> deleteCycleById(Long idCycle, Long idTontine);

    // MORE OPERATIONS //
    ResponseEntity<Object> findCycleById(Long idCycle);

    ResponseEntity<Object> findAllSessionsOfCycle(Long idCycle);

    ResponseEntity<Object> closeCycleById(Long idCycle);

    ResponseEntity<Object> findCycleByName(String name);

    Long countAll();
}
