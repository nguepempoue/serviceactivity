package com.b2i.activitiesorganisation.service.PaymentMethod;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.PaymentMethod.PaymentMethodRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.PaymentMethod;
import com.b2i.activitiesorganisation.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImplementation implements PaymentMethodService{


    @Autowired
    private PaymentMethodRepository paymentMethodRepository;


    // CREATE
    @Override
    public ResponseEntity<Object> createPaymentMethod(PaymentMethodRequest paymentMethodRequest) {

        try {

            // CHECK NAME
            Utils.checkStringValues(paymentMethodRequest.getLabel() , "Payment method label");

            // NEW PAYMENT METHOD
            return ResponseHandler.generateCreatedResponse("Payment method created !",
                    paymentMethodRepository.save(new PaymentMethod(paymentMethodRequest.getLabel())));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // GET ALL PAYMENT METHODS
    @Override
    public ResponseEntity<Object> findAllPaymentMethods() {

        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();

        try {

            if(paymentMethodList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Payment method list", paymentMethodList);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updatePaymentMethod(Long idPaymentMethod, PaymentMethodRequest paymentMethodRequest) {

        // GET PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        try {

            return paymentMethod.map(pm -> {

                if(paymentMethodRequest.getLabel() != null) {
                    pm.setLabel(paymentMethodRequest.getLabel());
                }
                return ResponseHandler.generateOkResponse("Payment method " + idPaymentMethod + " updated !",
                        paymentMethodRepository.save(pm));
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment method not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deletePaymentMethod(Long idPaymentMethod) {

        // GET PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        try {

            if(!paymentMethod.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Payment method not found !");
            }

            paymentMethodRepository.deleteById(idPaymentMethod);
            return ResponseHandler.generateOkResponse("Payment method " + idPaymentMethod + " deleted !", null);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findPaymentMethodById(Long idPaymentMethod) {

        // GET PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        try {

            return paymentMethod.map(pm -> ResponseHandler.generateOkResponse("Payment method " + idPaymentMethod, pm))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment method not found !"));

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT
    @Override
    public Long countAll() {
        return paymentMethodRepository.count();
    }
}
