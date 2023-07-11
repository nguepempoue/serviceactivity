package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Payment.PaymentRequest;
import com.b2i.activitiesorganisation.dto.request.Penalty.PenaltyRequest;
import com.b2i.activitiesorganisation.dto.request.Session.SessionRequest;
import com.b2i.activitiesorganisation.service.Payment.PaymentServiceImplementation;
import com.b2i.activitiesorganisation.service.Penalty.PenaltyServiceImplementation;
import com.b2i.activitiesorganisation.service.Session.SessionServiceImplementation;
import com.b2i.activitiesorganisation.service.UserPaymentState.UserPaymentStateServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@CrossOrigin("*")
public class SessionController {

    @Autowired
    private SessionServiceImplementation sessionServiceImplementation;

    @Autowired
    private PaymentServiceImplementation paymentServiceImplementation;

    @Autowired
    private PenaltyServiceImplementation penaltyServiceImplementation;

    @Autowired
    private UserPaymentStateServiceImplementation userPaymentStateServiceImplementation;


    // GET ALL SESSIONS
    @GetMapping
    public ResponseEntity<Object> findAllSessions() {
        return sessionServiceImplementation.findAllSessions();
    }


    // UPDATE SESSION
    @PutMapping("/{idSession}")
    public ResponseEntity<Object> updateSession(@PathVariable("idSession") Long idSession, @RequestBody SessionRequest sessionRequest) {
        return sessionServiceImplementation.updateSession(idSession, sessionRequest);
    }


    // FIND SESSION BY ID
    @GetMapping("/{idSession}")
    public ResponseEntity<Object> findSessionById(@PathVariable("idSession") Long idSession) {
        return sessionServiceImplementation.findSessionById(idSession);
    }


    // UPDATE CONTRIBUTION DEADLINE
    @PatchMapping("/{idSession}/contribution-deadline")
    public ResponseEntity<Object> updateContributionDeadline(@PathVariable("idSession") Long idSession, @RequestBody SessionRequest sessionRequest) {
        return sessionServiceImplementation.updateContributionDeadline(idSession, sessionRequest);
    }


    // CREATE A PAYMENT FOR A SESSION
    @PostMapping("/{idSession}/payment/user/{idUser}/payment-method/{idPaymentMethod}")
    public ResponseEntity<Object> createPaymentForSession(@RequestParam("token") String token, @PathVariable("idSession") Long idSession, @PathVariable("idUser") Long idUser, @PathVariable("idPaymentMethod") Long idPaymentMethod, @RequestBody PaymentRequest paymentRequest) {
        return paymentServiceImplementation.createPayment(token, idSession, idUser, idPaymentMethod, paymentRequest);
    }


    // GET ALL PAYMENTS OF A SESSION
    @GetMapping("/{idSession}/all-payments")
    public ResponseEntity<Object> findAllPaymentsOfASession(@PathVariable("idSession") Long idSession) {
        return paymentServiceImplementation.findAllPaymentsOfASession(idSession);
    }


    // GET ALL PENALTIES OF A SESSION
    @GetMapping("/{idSession}/all-penalties")
    public ResponseEntity<Object> findPenaltiesOfASession(@PathVariable("idSession") Long idSession) {
        return penaltyServiceImplementation.findPenaltiesOfASession(idSession);
    }


    // CREATE A PENALTY FOR A SESSION
    @PostMapping("/{idSession}/penalty/penalty-type/{idPenaltyType}/user/{idUser}")
    public ResponseEntity<Object> createPenaltyForSession(@RequestParam("token") String token, @PathVariable("idSession") Long idSession, @PathVariable("idPenaltyType") Long idPenaltyType, @PathVariable("idUser") Long idUser, @RequestBody PenaltyRequest penaltyRequest) {
        return penaltyServiceImplementation.createPenaltyForUserAndSession(token, idUser, idSession, idPenaltyType, penaltyRequest);
    }


    // GET USER PAYMENT STATE OF A SESSION
    @GetMapping("/{idSession}/payment-state/user/{idUser}")
    public ResponseEntity<Object> findUserPaymentStateByUserIdAndSession(@PathVariable("idSession") Long idSession, @PathVariable("idUser") Long userId) {
        return userPaymentStateServiceImplementation.findUserPaymentStateByUserIdAndSession(idSession, userId);
    }


    // GET ALL USER PAYMENT STATES OF A SESSION
    @GetMapping("/{idSession}/payment-states")
    public ResponseEntity<Object> findAllUserPaymentStateBySession(@PathVariable("idSession") Long idSession) {
        return userPaymentStateServiceImplementation.findAllUserPaymentStateBySession(idSession);
    }


    // GET ALL PAYMENTS BY USER AND SESSION
    @GetMapping("/{idSession}/all-payments/user/{idUser}")
    public ResponseEntity<Object> findAllPaymentByUserAndSession(@PathVariable("idUser") Long idUser, @PathVariable("idSession") Long idSession) {
        return paymentServiceImplementation.findAllPaymentByUserAndSession(idUser, idSession);
    }


    // GET ALL PENALTIES BY USER AND SESSION
    @GetMapping("/{idSession}/all-penalties/user/{idUser}")
    public ResponseEntity<Object> findAllPenaltiesByUserAndSession(@PathVariable("idUser") Long idUser, @PathVariable("idSession") Long idSession) {
        return penaltyServiceImplementation.findAllPenaltiesByUserAndSession(idUser, idSession);
    }


    // OPEN SESSION
    @PatchMapping("/{idSession}/open")
    public ResponseEntity<Object> openSessionById(@PathVariable("idSession") Long idSession) {
        return sessionServiceImplementation.openSessionById(idSession);
    }


    // CLOSE SESSION
    @PatchMapping("/{idSession}/close")
    public ResponseEntity<Object> closeSessionById(@PathVariable("idSession") Long idSession) {
        return sessionServiceImplementation.closeSessionById(idSession);
    }


    // GENERATE WINNER
    @PatchMapping("/{idSession}/generate-winner")
    public ResponseEntity<Object> generateWinnerOfASession(@RequestParam("token") String token,@PathVariable("idSession") Long idSession) {
        return sessionServiceImplementation.generateWinnerOfASession(token, idSession);
    }

}
