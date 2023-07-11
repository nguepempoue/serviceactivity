package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.Status.StatusRequest;
import com.b2i.activitiesorganisation.service.Status.StatusServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusBootstrap {

    @Autowired
    private StatusServiceImplementation statusServiceImplementation;

    public void seed() {

        if(statusServiceImplementation.countAllStatus() == 0L) {

            statusServiceImplementation.createStatus(new StatusRequest("OUVERT", "OUVERT"));
            statusServiceImplementation.createStatus(new StatusRequest("FERMÉ", "FERMÉ"));
            statusServiceImplementation.createStatus(new StatusRequest("CLOTURÉ", "CLOTURÉ"));
        }
    }
}
