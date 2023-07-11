package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.ProfitabilityType.ProfitabilityTypeRequest;
import com.b2i.activitiesorganisation.service.ProfitabilityType.ProfitabilityTypeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfitabilityTypeBootstrap {

    @Autowired
    private ProfitabilityTypeServiceImplementation profitabilityTypeServiceImplementation;

    public void seed() {

        if(profitabilityTypeServiceImplementation.countAll() == 0L) {

            profitabilityTypeServiceImplementation.createProfitabilityType(new ProfitabilityTypeRequest("CERTAIN"));
            profitabilityTypeServiceImplementation.createProfitabilityType(new ProfitabilityTypeRequest("INCERTAIN"));

        }
    }
}
