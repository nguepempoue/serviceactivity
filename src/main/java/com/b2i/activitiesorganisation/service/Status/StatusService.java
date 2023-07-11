package com.b2i.activitiesorganisation.service.Status;

import com.b2i.activitiesorganisation.dto.request.Status.StatusRequest;
import com.b2i.activitiesorganisation.model.Status;
import org.springframework.http.ResponseEntity;

public interface StatusService {

    // CRUD OPERATIONS //
    Status createStatus(StatusRequest statusRequest);

    ResponseEntity<Object> findAllStatus();

    ResponseEntity<Object> updateStatus(Long idStatus, StatusRequest statusRequest);

    ResponseEntity<Object> deleteStatus(Long idStatus);


    // MORE OPERATIONS //
    ResponseEntity<Object> findStatusById(Long idStatus);

    Long countAllStatus();
}
