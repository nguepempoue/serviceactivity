package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.DraweeForm.DraweeFormRequest;
import com.b2i.activitiesorganisation.service.DraweeForm.DraweeFormServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DraweeFormBootstrap {

    @Autowired
    private DraweeFormServiceImplementation draweeFormServiceImplementation;

    public void seed() {

        if(draweeFormServiceImplementation.countAll() == 0L) {

            draweeFormServiceImplementation.createDraweeForm(new DraweeFormRequest("ASSOCIATION", "ASSOCIATION"));
            draweeFormServiceImplementation.createDraweeForm(new DraweeFormRequest("PERSONNE PHYSIQUE", "PERSONNE PHYSIQUE"));
            draweeFormServiceImplementation.createDraweeForm(new DraweeFormRequest("TPE", "TPE"));
            draweeFormServiceImplementation.createDraweeForm(new DraweeFormRequest("PME", "PME"));
            draweeFormServiceImplementation.createDraweeForm(new DraweeFormRequest("MUTUALIST", "MUTUALIST"));
        }
    }
}
