package com.b2i.activitiesorganisation.service.RefundType;

import com.b2i.activitiesorganisation.dto.request.RefundType.RefundTypeRequest;
import org.springframework.http.ResponseEntity;

public interface RefundTypeService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createRefundType(RefundTypeRequest refundTypeRequest);

    ResponseEntity<Object> findAllRefundTypes();

    ResponseEntity<Object> updateRefundType(Long idType, RefundTypeRequest refundTypeRequest);

    ResponseEntity<Object> deleteRefundType(Long idType);


    // MORE OPERATIONS
    ResponseEntity<Object> findRefundTypeById(Long idType);

    Long countAll();
}
