package com.b2i.activitiesorganisation.service.ProfitabilityType;

import com.b2i.activitiesorganisation.dto.request.ProfitabilityType.ProfitabilityTypeRequest;
import com.b2i.activitiesorganisation.model.ProfitabilityType;
import org.springframework.http.ResponseEntity;

public interface ProfitabilityTypeService {

    // CRUD OPERATIONS //
    ProfitabilityType createProfitabilityType(ProfitabilityTypeRequest profitabilityTypeRequest);

    ResponseEntity<Object> findAllProfitabilityTypes();

    ResponseEntity<Object> updateProfitabilityType(Long idType, ProfitabilityTypeRequest profitabilityTypeRequest);

    ResponseEntity<Object> deleteProfitabilityType(Long idType);


    // MORE OPERATIONS //
    ResponseEntity<Object> findProfitabilityTypeById(Long idType);

    Long countAll();
}
