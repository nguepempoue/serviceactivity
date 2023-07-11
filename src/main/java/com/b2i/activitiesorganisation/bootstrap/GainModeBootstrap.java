package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.GainMode.GainModeRequest;
import com.b2i.activitiesorganisation.service.GainMode.GainModeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GainModeBootstrap {

    @Autowired
    private GainModeServiceImplementation gainModeServiceImplementation;

    public void seed() {

        if(gainModeServiceImplementation.countAllGainModes() == 0L) {

            gainModeServiceImplementation.createGainMode(new GainModeRequest("ENCHÈRES", "ENCHÈRES"));
            gainModeServiceImplementation.createGainMode(new GainModeRequest("TIRAGE AU SORT", "TIRAGE AU SORT"));
            gainModeServiceImplementation.createGainMode(new GainModeRequest("ORDRE", "ORDRE"));
        }
    }
}
