package com.b2i.activitiesorganisation.service.Penalty;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import com.b2i.activitiesorganisation.dto.request.Penalty.PenaltyRequest;
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
public class PenaltyServiceImplementation implements PenaltyService {


    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private PenaltyTypeRepository penaltyTypeRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TontineServiceImplementation tontineServiceImplementation;

    @Autowired
    private UserPenaltyStateRepository userPenaltyStateRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FeignService feignService;

    ObjectMapper mapper = new ObjectMapper();


    // CREATE PENALTY
    @Override
    public ResponseEntity<Object> createPenaltyForUserAndSession(String token, Long idUser, Long idSession, Long idPenaltyType, PenaltyRequest penaltyRequest) {

        String bearerToken = "Bearer " + token;

        // NEW PENALTY
        Penalty penalty = new Penalty();

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        // GET PENALTY TYPE
        Optional<PenaltyType> penaltyType = penaltyTypeRepository.findById(idPenaltyType);

        // GET TONTINE OF THIS SESSION
        Tontine tontine = tontineServiceImplementation.getTontineOfASession(idSession);

        try {

            // CHECK SESSION
            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            // VERIFY IS SESSION IS OPENED
            Utils.verifySessionStatus(session.get());


            // CHECK PENALTY TYPE
            if(!penaltyType.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Penalty userType not found !");
            }

            // CHECK DATE OF PENALTY
            if(penaltyRequest.getDate() == null) {
                throw new Exception("Penalty date can't be null !");
            }

            // CHECK USER
            User user = getUser(bearerToken, idUser);
            if(user == null) {
                return ResponseHandler.generateNotFoundResponse("User not found. Maybe it has been deleted !");
            }

            // CHECK IF USER IS MEMBER OF TONTINE
            Boolean test = false;

            // IF THERE'S ONE TONTINE MEMBER WHERE ID = ID USER, test = true
            // SO USER IS PARTICIPANT OF TONTINE
            for(TontineMember tm : tontine.getTontineMembers()) {
                if (tm.getParticipant().getId().equals(idUser)) {
                    test = true;
                    break;
                }
            }

            // ELSE USER ISN'T PARTICIPANT OF THIS TONTINE
            if(test.equals(false)) {
                throw new Exception("This user is not a participant of this tontine !");
            }

            // SETTING VALUES
            penalty.setDate(penaltyRequest.getDate());
            // penalty.setUserId(idUser);
            penalty.setUser(user);
            penalty.setPenaltyType(penaltyType.get());

            // UPDATE USER PENALTY STATE
            for(UserPenaltyState ups : session.get().getUserPenaltyStates()) {

                // GET USER PENALTY STATE OF USER (ID USER)
                if(ups.getUserId().equals(idUser)) {

                    ups.setPenaltiesToPay(ups.getPenaltiesToPay() + penaltyType.get().getAmount()); // SET AMOUNT TO PAY
                    ups.setRemainingToPay(ups.getRemainingToPay() + penaltyType.get().getAmount()); // SET REMAINING TO PAY BY USER
                    if(ups.getUpToDate()) { ups.setUpToDate(false); } // USER HAS NOW PENALTIES
                    userPenaltyStateRepository.save(ups);

                    break;
                }
            }

            // SAVING PENALTY
            penalty = penaltyRepository.save(penalty);

            // ADDING PENALTY TO SESSION
            Session s = session.get();
            s.getPenalties().add(penalty);
            s.setTotalPenalties(s.getTotalPenalties() + penalty.getPenaltyType().getAmount());
            sessionRepository.save(s);

            return ResponseHandler.generateCreatedResponse("Penalty added !", penalty);

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL PENALTIES
    @Override
    public ResponseEntity<Object> findAllPenalties() {

        // GET ALL PENALTIES
        List<Penalty> penaltyList = penaltyRepository.findAll();

        try {

            if(penaltyList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Penalties list is empty !");
            }
            return ResponseHandler.generateOkResponse("Penalties list : ", penaltyList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE PENALTY
    @Override
    public ResponseEntity<Object> updatePenalty(Long idPenalty, PenaltyRequest penaltyRequest) {

        // GET PENALTY
        Optional<Penalty> penalty = penaltyRepository.findById(idPenalty);

        try {

            return penalty.map((p) -> {

                // UPDATE
                if(penaltyRequest.getDate() != null) {
                    p.setDate(penaltyRequest.getDate());
                }

                return ResponseHandler.generateOkResponse("Penalty " + idPenalty + " has properly been updated !",
                        penaltyRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Penalty not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE PENALTY
    @Override
    public ResponseEntity<Object> deletePenalty(Long idPenalty) {

        // GET PENALTY
        Optional<Penalty> penalty = penaltyRepository.findById(idPenalty);

        try {

            return penalty.map((p) -> {
                penaltyRepository.deleteById(idPenalty);
                return ResponseHandler.generateOkResponse("Penalty " + idPenalty + "has properly been deleted", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Penalty not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND PENALTY BY ID
    @Override
    public ResponseEntity<Object> findPenaltyById(Long idPenalty) {

        // GET PENALTY
        Optional<Penalty> penalty = penaltyRepository.findById(idPenalty);

        try {

            return penalty.map((p) -> ResponseHandler.generateOkResponse("Penalty " + idPenalty, p))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Penalty not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // PAY PENALTY
    @Override
    public ResponseEntity<Object> payPenalty(String token, Long idPenalty, Long idUser, Long idPaymentMethod, PaymentRequest paymentRequest) {

        String bearerToken = "Bearer " + token;

        // GET PENALTY
        Optional<Penalty> penalty = penaltyRepository.findById(idPenalty);

        // GET PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        // GET TONTINE OF THIS
        Tontine tontine = new Tontine();

        try {

            // GET SESSION OF THIS PENALTY
            if(!penalty.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Penalty not found !");
            }

            // PAYMENT
            Utils.checkStringValues(paymentRequest.getProof(),"Payment proof");

            // if(paymentRequest.getPaid() == null) { throw new Exception("Payment amount can't be null !"); }
            if(paymentRequest.getDate() == null) { throw new Exception("Payment date can't be null !"); }
            if(!paymentMethod.isPresent()) { return ResponseHandler.generateNotFoundResponse("Payment method not found !"); }

            // NEW PAYMENT
            Payment payment = new Payment();
            payment.setDate(paymentRequest.getDate());
            payment.setProof(paymentRequest.getProof());
            payment.setUser(getUser(bearerToken, idUser));
            payment.setPaymentMethod(paymentMethod.get());
            payment.setPaid(penalty.get().getPenaltyType().getAmount());

            if(paymentStatusRepository.findPaymentStatusByLabel("ENREGISTRÉ").isPresent()) {
                payment.setPaymentStatus(paymentStatusRepository.findPaymentStatusByLabel("ENREGISTRÉ").get());
            }

            // SAVING PAYMENT
            payment = paymentRepository.save(payment);


            Penalty p = penalty.get();
            List<Session> sessions = sessionRepository.findAll(); // ALL SESSIONS

            for(Session s : sessions) {
                if(s.getPenalties().contains(p)) {
                    tontine = tontineServiceImplementation.getTontineOfASession(s.getId()); // GET TONTINE

                    for(UserPenaltyState u : s.getUserPenaltyStates()) {
                        if(u.getUserId().equals(idUser)) {

                            // CHECK USER PENALTY STATE
                            if(u.getUpToDate()) {
                                paymentRepository.deleteById(payment.getId());
                                throw new Exception("User is already up to date on his penalties !");
                            }

                            u.setPaidPenalties(u.getPaidPenalties() + payment.getPaid()); // SET PAID PENALTIES
                            u.setRemainingToPay(u.getRemainingToPay() - payment.getPaid()); // SET REMAINING AMOUNT TO PAY

                            // ADD PAYMENT TO PENALTY STATE
                            u.getPaymentList().add(payment);

                            if(u.getRemainingToPay() == 0L) {
                                u.setUpToDate(true); // SET USER UP TO DATE
                            }
                            userPenaltyStateRepository.save(u);
                            break;
                        }
                    }
                    break;
                }
            }

            // UPDATE ACCOUNT TONTINE WITH AMOUNT OF PENALTY
            tontine.setAccount(setBalance(bearerToken, tontine, p.getPenaltyType().getAmount()));

            // UPDATE PAID STATUS OF PENALTY
            p.setPaid(true);

            return ResponseHandler.generateOkResponse("Penalty " + idPenalty + " has properly been paid !",
                    penaltyRepository.save(p));

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL PENALTY
    @Override
    public Long countAllPenalties() {
        return penaltyRepository.count();
    }


    // FIND PENALTIES OF A SESSION
    @Override
    public ResponseEntity<Object> findPenaltiesOfASession(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            Session s = session.get();
            if(s.getPenalties().isEmpty()) {
                return ResponseHandler.generateNoContentResponse("There's no penalty in session " + idSession + " !");
            }

            return ResponseHandler.generateOkResponse("Penalties of session " + idSession, s.getPenalties());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND PENALTIES BY USER AND SESSION
    @Override
    public ResponseEntity<Object> findAllPenaltiesByUserAndSession(Long idUser, Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            // CHECK IF SESSION IS PRESENT
            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            // GET ALL PENALTIES OF USER (USER ID)
            List<Penalty> penalties = new ArrayList<>();
            for (Penalty p : session.get().getPenalties()) {
                if(p.getUser().getId().equals(idUser)) {
                    penalties.add(p);
                }
            }

            if(penalties.isEmpty()) {
                return ResponseHandler.generateNotFoundResponse("User " + idUser + " doesn't have penalties for session " + idSession + " !");
            }

            // RETURN
            return ResponseHandler.generateOkResponse("All penalties of user " + idUser + " for session " + idSession, penalties);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }




    /* **************************************** OTHERS **************************************** */




    // GET USER
    private User getUser(String bearerToken, Long id) {

        String rUser = ResponseStringifier.stringifier(feignService.getUserById(bearerToken, id).getBody());
        try {
            if (!rUser.equals("")) {
                return mapper.readValue(rUser, User.class);
            }
            else throw new Exception("User not found !");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // GET ACCOUNT AND UPDATE BALANCE
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
}
