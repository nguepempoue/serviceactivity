package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.PenaltyType.PenaltyTypeRequest;
import com.b2i.activitiesorganisation.service.PenaltyType.PenaltyTypeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenaltyTypeBootstrap {

    @Autowired
    private PenaltyTypeServiceImplementation penaltyServiceImplementation;

    public void seed() {

        if(penaltyServiceImplementation.countAllPenaltyTypes() == 0L) {

            penaltyServiceImplementation.createPenaltyType(new PenaltyTypeRequest("RETARD A LA SEANCE", 500L));
            penaltyServiceImplementation.createPenaltyType(new PenaltyTypeRequest("ABSENCE A LA SEANCE", 1000L));
            penaltyServiceImplementation.createPenaltyType(new PenaltyTypeRequest("NON TOTALEMENT PAYÉ", 1000L));
        }
    }
}
