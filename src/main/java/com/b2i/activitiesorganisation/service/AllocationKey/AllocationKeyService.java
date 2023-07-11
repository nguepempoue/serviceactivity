package com.b2i.activitiesorganisation.service.AllocationKey;

import com.b2i.activitiesorganisation.dto.request.AllocationKey.AllocationKeyRequest;
import com.b2i.activitiesorganisation.model.AllocationKey;
import org.springframework.http.ResponseEntity;

public interface AllocationKeyService {

    // CRUD OPERATIONS //
    AllocationKey createAllocationKey(AllocationKeyRequest allocationKeyRequest, Long idReceivingParty);

    ResponseEntity<Object> findAllAllocationKeys();

    ResponseEntity<Object> updateAllocationKey(Long idAllocationKey, AllocationKeyRequest allocationKeyRequest);

    ResponseEntity<Object> deleteAllocationKey(Long idAllocationKey);


    // MORE OPERATIONS //
    ResponseEntity<Object> findAllocationKey(Long idAllocationKey);

    Long countAll();
}
