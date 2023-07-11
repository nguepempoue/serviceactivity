package com.b2i.activitiesorganisation.bootstrap;

import com.b2i.activitiesorganisation.dto.request.PaymentMethod.PaymentMethodRequest;
import com.b2i.activitiesorganisation.service.PaymentMethod.PaymentMethodServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodBootstrap {

    @Autowired
    private PaymentMethodServiceImplementation paymentMethodServiceImplementation;

    public void seed() {

        if(paymentMethodServiceImplementation.countAll() == 0L) {

            paymentMethodServiceImplementation.createPaymentMethod(new PaymentMethodRequest("CHÈQUE"));
            paymentMethodServiceImplementation.createPaymentMethod(new PaymentMethodRequest("VIREMENT BANCAIRE"));
            paymentMethodServiceImplementation.createPaymentMethod(new PaymentMethodRequest("MOOV MONEY"));
            paymentMethodServiceImplementation.createPaymentMethod(new PaymentMethodRequest("ORANGE MONEY"));
            paymentMethodServiceImplementation.createPaymentMethod(new PaymentMethodRequest("MTN MOMO"));
        }
    }
}
