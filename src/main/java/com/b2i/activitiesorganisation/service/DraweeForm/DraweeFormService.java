package com.b2i.activitiesorganisation.service.DraweeForm;

import com.b2i.activitiesorganisation.dto.request.DraweeForm.DraweeFormRequest;
import org.springframework.http.ResponseEntity;

public interface DraweeFormService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createDraweeForm(DraweeFormRequest draweeFormRequest);

    ResponseEntity<Object> findAllDraweeForm();

    ResponseEntity<Object> updateDraweeForm(Long idForm, DraweeFormRequest draweeFormRequest);

    ResponseEntity<Object> deleteDraweeForm(Long idForm);


    // MORE OPERATIONS //
    ResponseEntity<Object> findDraweeFormById(Long idForm);

    Long countAll();
}
