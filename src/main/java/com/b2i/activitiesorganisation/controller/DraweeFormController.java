package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.DraweeForm.DraweeFormRequest;
import com.b2i.activitiesorganisation.service.DraweeForm.DraweeFormServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drawee-forms")
@CrossOrigin("*")
public class DraweeFormController {

    @Autowired
    private DraweeFormServiceImplementation draweeFormServiceImplementation;


    // CREATE
    @PostMapping
    public ResponseEntity<Object> createDraweeForm(@RequestBody DraweeFormRequest draweeFormRequest) {
        return draweeFormServiceImplementation.createDraweeForm(draweeFormRequest);
    }


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAllDraweeForm() {
        return draweeFormServiceImplementation.findAllDraweeForm();
    }


    // UPDATE
    @PutMapping("/{idForm}")
    public ResponseEntity<Object> updateDraweeForm(@PathVariable("idForm") Long idForm, @RequestBody DraweeFormRequest draweeFormRequest) {
        return draweeFormServiceImplementation.updateDraweeForm(idForm, draweeFormRequest);
    }


    // DELETE
    @DeleteMapping("/{idForm}")
    public ResponseEntity<Object> deleteDraweeForm(@PathVariable("idForm") Long idForm) {
        return draweeFormServiceImplementation.deleteDraweeForm(idForm);
    }


    // FIND BY ID
    @GetMapping("/{idForm}")
    public ResponseEntity<Object> findDraweeFormById(@PathVariable("idForm") Long idForm) {
        return draweeFormServiceImplementation.findDraweeFormById(idForm);
    }
}
