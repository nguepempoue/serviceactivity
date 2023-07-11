package com.b2i.activitiesorganisation.service.DraweeForm;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.DraweeForm.DraweeFormRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.DraweeForm;
import com.b2i.activitiesorganisation.repository.DraweeFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DraweeFormServiceImplementation implements DraweeFormService {

    @Autowired
    private DraweeFormRepository draweeFormRepository;


    // CREATE
    @Override
    public ResponseEntity<Object> createDraweeForm(DraweeFormRequest draweeFormRequest) {

        try {

            // CHECK STRING VALUES
            Utils.checkStringValues(draweeFormRequest.getLabel(), "Drawee form label");

            // NEW DRAWEE FORM
            DraweeForm form = new DraweeForm();

            form.setLabel(draweeFormRequest.getLabel()); // LABEL
            if(draweeFormRequest.getDescription() != null) {
                form.setDescription(draweeFormRequest.getDescription()); // DESCRIPTION
            }
            else {
                form.setDescription(draweeFormRequest.getLabel());
            }

            // SAVE
            return ResponseHandler.generateCreatedResponse("Drawee form created !", draweeFormRepository.save(form));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllDraweeForm() {

        // GET ALL
        List<DraweeForm> formList = draweeFormRepository.findAll();

        try {
            if(formList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Drawee form list", formList);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateDraweeForm(Long idForm, DraweeFormRequest draweeFormRequest) {

        // GET DRAWEE FORM
        Optional<DraweeForm> form = draweeFormRepository.findById(idForm);

        try {

            return form.map(f -> {

                if(draweeFormRequest.getLabel() != null) { f.setLabel(draweeFormRequest.getLabel()); }

                if(draweeFormRequest.getDescription() != null) { f.setDescription(draweeFormRequest.getDescription()); }

                return ResponseHandler.generateOkResponse("Drawee form updated !", draweeFormRepository.save(f));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Drawee form not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deleteDraweeForm(Long idForm) {

        // GET DRAWEE FORM
        Optional<DraweeForm> form = draweeFormRepository.findById(idForm);

        try {
            if(!form.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Drawee not found !");
            }

            draweeFormRepository.deleteById(idForm);
            return ResponseHandler.generateOkResponse("Drawee form deleted !", null);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findDraweeFormById(Long idForm) {

        // GET DRAWEE FORM
        Optional<DraweeForm> form = draweeFormRepository.findById(idForm);

        try {
            return form.map(f -> ResponseHandler.generateOkResponse("Drawee form " + idForm, f))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Drawee form not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // COUNT ALL
    @Override
    public Long countAll() {
        return draweeFormRepository.count();
    }
}
