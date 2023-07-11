package com.b2i.activitiesorganisation.service.PenaltyType;

import com.b2i.activitiesorganisation.dto.request.PenaltyType.PenaltyTypeRequest;
import com.b2i.activitiesorganisation.model.PenaltyType;
import org.springframework.http.ResponseEntity;

public interface PenaltyTypeService {

    // CRUD OPERATIONS //
    PenaltyType createPenaltyType(PenaltyTypeRequest penaltyTypeRequest);

    ResponseEntity<Object> findAllPenaltyTypes();

    ResponseEntity<Object> updatePenaltyType(Long idPenaltyType, PenaltyTypeRequest penaltyTypeRequest);

    ResponseEntity<Object> deletePenaltyType(Long idPenaltyType);


    // MORE OPERATIONS //
    ResponseEntity<Object> findPenaltyTypeById(Long idPenaltyType);

    Long countAllPenaltyTypes();
}
