package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.Frequency.FrequencyRequest;
import com.b2i.activitiesorganisation.service.Frequency.FrequencyServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrequencyBootstrap {

    @Autowired
    private FrequencyServiceImplementation frequencyServiceImplementation;

    public void seed() {

        if(frequencyServiceImplementation.countAll() == 0L) {

            frequencyServiceImplementation.createFrequency(new FrequencyRequest("HEBDOMADAIRE", "HEBDOMADAIRE"));
            frequencyServiceImplementation.createFrequency(new FrequencyRequest("MENSUELLE", "MENSUELLE"));
            frequencyServiceImplementation.createFrequency(new FrequencyRequest("BIMENSUELLE", "BIMENSUELLE"));
            frequencyServiceImplementation.createFrequency(new FrequencyRequest("TRIMESTRIELLE", "TRIMESTRIELLE"));
            frequencyServiceImplementation.createFrequency(new FrequencyRequest("SEMESTRIELLE", "SEMESTRIELLE"));
            frequencyServiceImplementation.createFrequency(new FrequencyRequest("ANNUELLE", "ANNUELLE"));

        }

    }
}
