package com.b2i.activitiesorganisation.service.UserPaymentState;

import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.Session;
import com.b2i.activitiesorganisation.model.UserPaymentState;
import com.b2i.activitiesorganisation.repository.SessionRepository;
import com.b2i.activitiesorganisation.repository.UserPaymentStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserPaymentStateServiceImplementation implements UserPaymentStateService{


    @Autowired
    private UserPaymentStateRepository userPaymentStateRepository;

    @Autowired
    private SessionRepository sessionRepository;


    // FIND ALL USER PAYMENT STATES
    @Override
    public ResponseEntity<Object> findAllUserPaymentStates() {

        // ALL USER PAYMENT STATES
        List<UserPaymentState> paymentStates = userPaymentStateRepository.findAll();

        try {

            if(paymentStates.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Payment states list is empty !");
            }
            return ResponseHandler.generateOkResponse("Payment states list", paymentStates);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // GET USER PAYMENT STATE BY ID
    @Override
    public ResponseEntity<Object> findUserPaymentStateById(Long idUserPaymentState) {

        // GET USER PAYMENT STATE
        Optional<UserPaymentState> paymentState = userPaymentStateRepository.findById(idUserPaymentState);

        try {

            return paymentState.map((ps) -> ResponseHandler.generateOkResponse("Payment state " + idUserPaymentState, ps))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment state nof found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND USER PAYMENT STATE BY USER ID
    @Override
    public ResponseEntity<Object> findUserPaymentStateByUserIdAndSession(Long idSession, Long userId) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            UserPaymentState ups = null;

            for(UserPaymentState u : session.get().getUserPaymentStates()) {

                // SEARCHING FOR USER PAYMENT STATE OF USER (USER ID)
                if(u.getUserId().equals(userId)) {
                    ups = u;
                    break;
                }
            }

            if(ups == null) {
                return ResponseHandler.generateNotFoundResponse("User payment state for this user not found !");
            }

            return ResponseHandler.generateOkResponse("Payment state", ups);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // PAYMENT STATES BY SESSION
    @Override
    public ResponseEntity<Object> findAllUserPaymentStateBySession(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            List<UserPaymentState> paymentStates = new ArrayList<>(session.get().getUserPaymentStates());

            if(paymentStates.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Payments states of session " + idSession + " is empty !");
            }
            else {
                return ResponseHandler.generateOkResponse("Payment states of session " + idSession, paymentStates);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }
}
