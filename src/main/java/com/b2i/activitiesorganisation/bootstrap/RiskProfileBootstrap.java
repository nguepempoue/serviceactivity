package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.RiskProfile.RiskProfileRequest;
import com.b2i.activitiesorganisation.service.RiskProfile.RiskProfileServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RiskProfileBootstrap {

    @Autowired
    private RiskProfileServiceImplementation riskProfileServiceImplementation;

    public void seed() {

        if(riskProfileServiceImplementation.countAll() == 0L) {

            riskProfileServiceImplementation.createRiskProfile(new RiskProfileRequest(1L));
            riskProfileServiceImplementation.createRiskProfile(new RiskProfileRequest(2L));
            riskProfileServiceImplementation.createRiskProfile(new RiskProfileRequest(3L));
            riskProfileServiceImplementation.createRiskProfile(new RiskProfileRequest(4L));
            riskProfileServiceImplementation.createRiskProfile(new RiskProfileRequest(5L));
        }
    }
}
