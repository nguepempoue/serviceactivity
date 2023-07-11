package com.b2i.activitiesorganisation.service.PaymentStatus;

import com.b2i.activitiesorganisation.dto.request.PaymentStatus.PaymentStatusRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.PaymentStatus;
import com.b2i.activitiesorganisation.repository.PaymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentStatusServiceImplementation implements PaymentStatusService {


    @Autowired
    private PaymentStatusRepository paymentStatusRepository;


    // CREATE PAYMENT STATUS
    @Override
    public ResponseEntity<Object> createPaymentStatus(PaymentStatusRequest paymentStatusRequest) {

        // NEW PAYMENT STATUS
        PaymentStatus paymentStatus = new PaymentStatus();

        try {
            if(paymentStatusRequest.getLabel() == null || paymentStatusRequest.getLabel().equals("")) {
                throw new Exception("Payment status label can't be null or empty string !");
            }

            paymentStatus.setLabel(paymentStatusRequest.getLabel());
            return ResponseHandler.generateCreatedResponse("Payment status created !", paymentStatusRepository.save(paymentStatus));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL PAYMENT STATUS
    @Override
    public ResponseEntity<Object> findAllPaymentStatus() {

        // GET ALL
        List<PaymentStatus> paymentStatusList = paymentStatusRepository.findAll();

        try {

            if(paymentStatusList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Payment status list is empty !");
            }
            else {
                return ResponseHandler.generateOkResponse("Payment status list", paymentStatusList);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE PAYMENT STATUS
    @Override
    public ResponseEntity<Object> updatePaymentStatus(Long idPaymentStatus, PaymentStatusRequest paymentStatusRequest) {

        // GET
        Optional<PaymentStatus> paymentStatus = paymentStatusRepository.findById(idPaymentStatus);

        try {

            if(!paymentStatus.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Payment status not found !");
            }

            if(paymentStatusRequest.getLabel() != null && !paymentStatusRequest.getLabel().equals("")) {
                paymentStatus.get().setLabel(paymentStatusRequest.getLabel());
            }

            return ResponseHandler.generateOkResponse("Payment status " + idPaymentStatus + " has properly been updated !",
                    paymentStatusRepository.save(paymentStatus.get()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE PAYMENT STATUS
    @Override
    public ResponseEntity<Object> deletePaymentStatus(Long idPaymentStatus) {

        // GET
        Optional<PaymentStatus> paymentStatus = paymentStatusRepository.findById(idPaymentStatus);

        try {

            return paymentStatus.map((ps) -> {
                paymentStatusRepository.deleteById(idPaymentStatus);
                return ResponseHandler.generateOkResponse("Payment status " + idPaymentStatus + " has properly been deleted !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment Status not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND PAYMENT STATUS BY ID
    @Override
    public ResponseEntity<Object> findPaymentStatusById(Long idPaymentStatus) {

        Optional<PaymentStatus> paymentStatus = paymentStatusRepository.findById(idPaymentStatus);

        try {

            return paymentStatus.map((ps) -> ResponseHandler.generateOkResponse("Payment status " + idPaymentStatus, ps))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment status not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL
    @Override
    public Long countAll() {
        return paymentStatusRepository.count();
    }
}
