package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.RefundType.RefundTypeRequest;
import com.b2i.activitiesorganisation.service.RefundType.RefundTypeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefundTypeBootstrap {

    @Autowired
    private RefundTypeServiceImplementation refundTypeServiceImplementation;

    public void seed() {

        if(refundTypeServiceImplementation.countAll() == 0L) {

            refundTypeServiceImplementation.createRefundType(new RefundTypeRequest("A L'ÉCHÉANCE"));
            refundTypeServiceImplementation.createRefundType(new RefundTypeRequest("PÉRIODIQUEMENT"));
            refundTypeServiceImplementation.createRefundType(new RefundTypeRequest("AVEC DIFFÉRÉ"));
        }
    }
}
