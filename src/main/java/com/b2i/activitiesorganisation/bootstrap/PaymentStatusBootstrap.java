package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.PaymentStatus.PaymentStatusRequest;
import com.b2i.activitiesorganisation.service.PaymentStatus.PaymentStatusServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusBootstrap {

    @Autowired
    private PaymentStatusServiceImplementation paymentStatusServiceImplementation;

    public void seed() {

        if(paymentStatusServiceImplementation.countAll() == 0L) {

            paymentStatusServiceImplementation.createPaymentStatus(new PaymentStatusRequest("ENREGISTRÉ"));
            paymentStatusServiceImplementation.createPaymentStatus(new PaymentStatusRequest("CONTROLÉ"));
            paymentStatusServiceImplementation.createPaymentStatus(new PaymentStatusRequest("VALIDÉ"));
        }
    }
}
