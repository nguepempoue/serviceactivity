package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.TransversalityLevel.TransversalityLevelRequest;
import com.b2i.activitiesorganisation.service.TransversalityLevel.TransversalityLevelServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransversalityLevelBootstrap {

    @Autowired
    private TransversalityLevelServiceImplementation implementation;

    public void seed() {

        if(implementation.countAllLevels() == 0L) {
            implementation.createLevel(new TransversalityLevelRequest("CLUB", "CLUB"));
            implementation.createLevel(new TransversalityLevelRequest("ZONE", "ZONE"));
            implementation.createLevel(new TransversalityLevelRequest("CENTRE", "CENTRE"));
        }
    }
}
