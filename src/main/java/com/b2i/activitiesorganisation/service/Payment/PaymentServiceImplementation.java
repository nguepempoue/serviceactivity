package com.b2i.activitiesorganisation.service.Payment;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.model.feignEntities.User;
import com.b2i.activitiesorganisation.repository.*;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.b2i.activitiesorganisation.service.Tontine.TontineServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private FeignService feignService;

    @Autowired
    private TontineServiceImplementation tontineServiceImplementation;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private UserPaymentStateRepository userPaymentStateRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private TontineRepository tontineRepository;


    ObjectMapper mapper = new ObjectMapper();


    // CREATE PAYMENT AND ADD IT TO A SESSION
    @Override
    public ResponseEntity<Object> createPayment(String token, Long idSession, Long idUser, Long idPaymentMethod, PaymentRequest paymentRequest) {

        String bearerToken = "Bearer " + token;
        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        // GET TONTINE OF THIS SESSION
        Tontine tontine = tontineServiceImplementation.getTontineOfASession(idSession);

        // GET PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        // NEW PAYMENT
        Payment payment = new Payment();

        try {

            // CHECK SESSION
            if (!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            // VERIFY IF SESSION IS OPENED
            Utils.verifySessionStatus(session.get());

            // CHECK PAYMENT METHOD
            if (!paymentMethod.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Payment method not found !");
            }

            // CHECK DATE
            if (paymentRequest.getDate() == null) {
                throw new Exception("Payment date can't be null !");
            }

            // CHECK AMOUNT PAID
            if (paymentRequest.getPaid() == null) {
                throw new Exception("Amount paid can't be null !");
            }

            // CHECK USER
            User user = getUser(bearerToken, idUser);
            if (user == null) {
                return ResponseHandler.generateNotFoundResponse("User not found. Maybe it has been deleted !");
            }

            // CHECK IF USER IS MEMBER OF TONTINE
            Boolean test = false;
            for(TontineMember tm : tontine.getTontineMembers()) {
                if (tm.getParticipant().getId().equals(idUser)) {
                    test = true;
                    break;
                }
            }
            if(test.equals(false)) {
                throw new Exception("This user is not a participant of this tontine !");
            }

            // CHECK REASON
            if (paymentRequest.getProof() == null || paymentRequest.getProof().equals("")) {
                throw new Exception("Proof can't be nor null neither an empty string");
            }


            // SETTING VALUES IF ALL TESTS ARE GOOD
            payment.setDate(paymentRequest.getDate());
            payment.setPaid(paymentRequest.getPaid());
            payment.setUser(user);
            payment.setProof(paymentRequest.getProof());

            // UPDATE TONTINE ACCOUNT
            tontine.setAccount(setBalance(bearerToken, tontine, payment.getPaid()));
            tontineRepository.save(tontine);

            // PAYMENT STATUS
            if (paymentStatusRepository.findPaymentStatusByLabel("ENREGISTRÉ").isPresent()) {
                payment.setPaymentStatus(paymentStatusRepository.findPaymentStatusByLabel("ENREGISTRÉ").get());
            }

            // PAYMENT METHOD
            payment.setPaymentMethod(paymentMethod.get());

            // SAVING PAYMENT
            payment = paymentRepository.save(payment);

            // UPDATING PAYMENT STATE OF USER
            for (UserPaymentState ups : session.get().getUserPaymentStates()) {

                if (ups.getUserId().equals(idUser)) {

                    // IF USER IS UP TO DATE
                    if (ups.getUpToDate()) {
                        return ResponseHandler.generateError(new Exception("User is already up to date on his payments !"));
                    }

                    // ADD PAYMENT TO PAYMENT STATE
                    ups.getUserPayments().add(payment);

                    // UPDATE REMAINING TO PAY
                    ups.setRemainingToPayByUser(ups.getRemainingToPayByUser() - payment.getPaid());

                    // UPDATE STATE IF USER IS UP TO DATE ON HIS PAYMENTS
                    if (ups.getRemainingToPayByUser() == 0L) {
                        ups.setUpToDate(true);
                    }
                    userPaymentStateRepository.save(ups);
                    break;
                }
            }


            // ADDING PAYMENT TO SESSION
            Session s = session.get();
            s.getPayments().add(payment);
            s.setTotalPaid(s.getTotalPaid() + payment.getPaid());
            sessionRepository.save(s);

            return ResponseHandler.generateCreatedResponse("Payment created !", payment);
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND ALL PAYMENTS
    @Override
    public ResponseEntity<Object> findAllPayments() {

        // GET ALL PAYMENTS
        List<Payment> payments = paymentRepository.findAll();

        try {

            if (payments.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Payments list is empty !");
            }

            return ResponseHandler.generateOkResponse("Payments list", payments);
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE PAYMENT
    @Override
    public ResponseEntity<Object> updatePayment(Long idPayment, PaymentRequest paymentRequest) {

        // GET PAYMENT
        Optional<Payment> payment = paymentRepository.findById(idPayment);

        try {

            return payment.map((p) -> {

                // UPDATE REASON
                if (paymentRequest.getProof() != null && !paymentRequest.getProof().equals("")) {
                    p.setProof(paymentRequest.getProof());
                }

                return ResponseHandler.generateOkResponse("Payment " + idPayment + " has been updated properly !",
                        paymentRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));

        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE PAYMENT
    @Override
    public ResponseEntity<Object> deletePayment(Long idPayment) {

        // GET PAYMENT
        Optional<Payment> payment = paymentRepository.findById(idPayment);

        try {
            return payment.map((p) -> {
                paymentRepository.deleteById(idPayment);
                return ResponseHandler.generateOkResponse("Payment " + idPayment + " has properly been deleted !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND PAYMENT BY ID
    @Override
    public ResponseEntity<Object> findPaymentById(Long idPayment) {

        // GET PAYMENT
        Optional<Payment> payment = paymentRepository.findById(idPayment);

        try {
            return payment.map(p -> ResponseHandler.generateOkResponse("Payment " + idPayment, p))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND PAYMENTS OF A SESSION
    @Override
    public ResponseEntity<Object> findAllPaymentsOfASession(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            return session.map((s) -> {

                // GET ALL PAYMENTS
                List<Payment> payments = new ArrayList<>(s.getPayments());

                return ResponseHandler.generateOkResponse("Payments of session " + idSession, payments);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));

        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // COUNT ALL PAYMENTS
    @Override
    public Long countAllPayments() {
        return paymentRepository.count();
    }


    // CHECK PAYMENT
    @Override
    public ResponseEntity<Object> checkPayment(Long idPayment) {
        return changePaymentStatus(idPayment, "CONTROLÉ");
    }


    // VALIDATE PAYMENT
    @Override
    public ResponseEntity<Object> validatePayment(Long idPayment) {
        return changePaymentStatus(idPayment, "VALIDÉ");
    }


    // FIND ALL PAYMENTS BY USER AND SESSION
    @Override
    public ResponseEntity<Object> findAllPaymentByUserAndSession(Long idUser, Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            // CHECK IF PRESENT
            if (!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            // GET PAYMENT STATE OF USER
            UserPaymentState ups = new UserPaymentState();
            for (UserPaymentState userPaymentState : session.get().getUserPaymentStates()) {

                if (userPaymentState.getUserId().equals(idUser)) {
                    ups = userPaymentState;
                    break;
                }
            }

            // GET ALL PAYMENTS OF USER (USER ID)
            List<Payment> payments = new ArrayList<>(ups.getUserPayments());

            if (payments.isEmpty()) {
                return ResponseHandler.generateNotFoundResponse("User " + idUser + " doesn't have payments for session " + idSession + " !");
            }

            // RETURN
            return ResponseHandler.generateOkResponse("All payments of user " + idUser + " for session " + idSession, payments);
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }





    /* **************************************** OTHERS **************************************** */




    // GET USER
    private User getUser(String bearerToken, Long id) {

        String rUser = ResponseStringifier.stringifier(feignService.getUserById(bearerToken, id).getBody());
        try {
            if (!rUser.equals("")) {
                return mapper.readValue(rUser, User.class);
            } else throw new Exception("User not found !");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // GET ACCOUNT AND UPDATE BALANCE
    private Account setBalance(String bearerToken, Tontine t, Long amount) {

        String rAccount = ResponseStringifier.stringifier(feignService.setBalance(bearerToken, t.getAccount().getId(), amount).getBody());
        try {
            return mapper.readValue(rAccount, Account.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // CHANGE PAYMENT STATUS
    private ResponseEntity<Object> changePaymentStatus(Long idPayment, String paymentStatus) {

        // GET PAYMENT
        Optional<Payment> payment = paymentRepository.findById(idPayment);

        // GET PAYMENT STATUS
        Optional<PaymentStatus> paymentStatus1 = paymentStatusRepository.findPaymentStatusByLabel(paymentStatus);

        try {

            if (!paymentStatus1.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Payment status not found !");
            }

            PaymentStatus ps = paymentStatus1.get();

            return payment.map(p -> {

                p.setPaymentStatus(ps);

                return ResponseHandler.generateOkResponse("Payment " + idPayment + " status is now " + paymentStatus,
                        paymentRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }
}
