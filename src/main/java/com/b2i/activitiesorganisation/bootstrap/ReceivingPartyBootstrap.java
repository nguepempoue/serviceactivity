package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.ReceivingParty.ReceivingPartyRequest;
import com.b2i.activitiesorganisation.service.ReceivingParty.ReceivingPartyServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceivingPartyBootstrap {

    @Autowired
    private ReceivingPartyServiceImplementation receivingPartyServiceImplementation;

    public void seed() {

        if(receivingPartyServiceImplementation.countAll() == 0L) {
            receivingPartyServiceImplementation.createReceivingParty(new ReceivingPartyRequest("POURVOYEURS DE FONDS"));
            receivingPartyServiceImplementation.createReceivingParty(new ReceivingPartyRequest("CAUTIONS"));
            receivingPartyServiceImplementation.createReceivingParty(new ReceivingPartyRequest("MUTUELLE"));
            receivingPartyServiceImplementation.createReceivingParty(new ReceivingPartyRequest("FONDS DE REVENUS PASSIFS"));
        }
    }
}
