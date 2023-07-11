package com.b2i.activitiesorganisation.service.Session;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.Session.SessionRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.repository.CycleRepository;
import com.b2i.activitiesorganisation.repository.SessionRepository;
import com.b2i.activitiesorganisation.repository.StatusRepository;
import com.b2i.activitiesorganisation.repository.TontineRepository;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.b2i.activitiesorganisation.service.Tontine.TontineServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionServiceImplementation implements SessionService {


    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private FeignService feignService;

    @Autowired
    private TontineRepository tontineRepository;

    @Autowired
    private TontineServiceImplementation tontineServiceImplementation;


    // CREATE SESSION
    @Override
    public ResponseEntity<Object> createSession(Long idCycle, SessionRequest sessionRequest) {

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        // NEW SESSION
        Session session = new Session();

        try {

            if(sessionRequest.getDate() == null) {
                throw new Exception("Session date can't be null !");
            }

            if(sessionRequest.getHour() == null) {
                throw new Exception("Session hour can't be null !");
            }

            if(sessionRequest.getContributionDeadline() == null) {
                throw new Exception("Contribution deadline can't be null !");
            }

            session.setDate(sessionRequest.getDate());
            session.setHour(sessionRequest.getHour());
            session.setContributionDeadline(sessionRequest.getContributionDeadline());

            return cycle.map((c) -> {

                // SAVING SESSION
                Session s = sessionRepository.save(session);

                // ADDING SESSION TO CYCLE
                c.getSessions().add(s);
                cycleRepository.save(c);

                return ResponseHandler.generateCreatedResponse("Session created !", s);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Cycle not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL SESSIONS
    @Override
    public ResponseEntity<Object> findAllSessions() {

        // GET ALL SESSIONS
        List<Session> sessions = sessionRepository.findAll();

        try {
            if(sessions.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Session list is empty !");
            }

            return ResponseHandler.generateOkResponse("Session list", sessions);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE SESSION
    @Override
    public ResponseEntity<Object> updateSession(Long id, SessionRequest sessionRequest) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(id);

        try {
            return session.map((s) -> {
                if(sessionRequest.getDate() != null) {
                    s.setDate(sessionRequest.getDate());
                }

                if(sessionRequest.getHour() != null) {
                    s.setHour(sessionRequest.getHour());
                }

                if(sessionRequest.getContributionDeadline() != null) {
                    s.setContributionDeadline(sessionRequest.getContributionDeadline());
                }

                s = sessionRepository.save(s);

                return ResponseHandler.generateOkResponse("Session " + id + " updated !", s);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE SESSION
    @Override
    public ResponseEntity<Object> deleteSessionOfACycle(Long idSession, Long idCycle) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        try {
            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            if(!cycle.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Cycle not found !");
            }

            Cycle c = cycle.get();
            c.getSessions().remove(session.get());
            cycleRepository.save(c);

            // DELETE
            sessionRepository.deleteById(idSession);
            return ResponseHandler.generateOkResponse("Session " + idSession + " has properly been deleted !", null);

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND SESSION BY ID
    @Override
    public ResponseEntity<Object> findSessionById(Long id) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(id);

        try {

            return session.map(s -> ResponseHandler.generateOkResponse("Session " + id, s))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE CONTRIBUTION DEADLINE
    @Override
    public ResponseEntity<Object> updateContributionDeadline(Long idSession, SessionRequest sessionRequest) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            return session.map((s) -> {

                // IF CONTRIBUTION DEADLINE IS NULL
                if(sessionRequest.getContributionDeadline() == null) {
                    return ResponseHandler.generateError(new Exception("Contribution deadline can't be updated if null !"));
                }

                // ELSE
                s.setContributionDeadline(sessionRequest.getContributionDeadline());
                s = sessionRepository.save(s);

                return ResponseHandler.generateOkResponse("Contribution deadline of session " + idSession +
                        " has properly been updated !", s);

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // OPEN SESSION BY ID
    @Override
    public ResponseEntity<Object> openSessionById(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            return session.map(s -> {

                if(s.getStatus().getLabel().equals("CLOTURÉ")) {
                    return ResponseHandler.generateError(new Exception("This session can't be reopened ! Status is CLOTURÉ"));
                }

                if(statusRepository.findStatusByLabel("OUVERT").isPresent()) {
                    s.setStatus(statusRepository.findStatusByLabel("OUVERT").get());
                }
                s = sessionRepository.save(s);

                return ResponseHandler.generateOkResponse("Session " + idSession + " has properly been opened !", s);

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // CLOSE SESSION
    @Override
    public ResponseEntity<Object> closeSessionById(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            return session.map((s) -> {

                // VERIFY CONTRIBUTIONS
                if(!verifyContributions(s)) {
                   return ResponseHandler.generateError(new Exception("All users are not up to date on their payments !"));
                }

                // VERIFY PENALTIES
                // if(!verifyPenalties(s)) { return ResponseHandler.generateError(new Exception("All users are not up to date on their penalties !")); }

                // ELSE
                if(statusRepository.findStatusByLabel("CLOTURÉ").isPresent()) {
                    s.setStatus(statusRepository.findStatusByLabel("CLOTURÉ").get());
                }
                s = sessionRepository.save(s);

                // GET CYCLE OF SESSION
                List<Cycle> cycles = cycleRepository.findAll();
                for(Cycle c : cycles) {

                    if(c.getSessions().contains(s)) {
                        c.setNumberOfSessionPassed(c.getNumberOfSessionPassed() + 1); // UPDATE NUMBER OF PASSED SESSIONS
                        c.setNumberOfSessionRemaining(c.getNumberOfSessionRemaining() - 1); // UPDATE NUMBER OF REMAINING SESSIONS
                        cycleRepository.save(c);
                        break;
                    }
                }

                return ResponseHandler.generateOkResponse("Session " + idSession + " has properly been closed !", s);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL SESSIONS
    @Override
    public Long countAllSessions() {
        return sessionRepository.count();
    }


    // GENERATE WINNER FOR A SESSION
    @Override
    public ResponseEntity<Object> generateWinnerOfASession(String token, Long idSession) {

        String bearerToken = "Bearer " + token;

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            // PRESENCE OF SESSION
            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            Session s = session.get();

            // GET TONTINE OF THIS SESSION
            Tontine tontine = tontineServiceImplementation.getTontineOfASession(idSession);

            // GET CYCLE OF THIS SESSION (CONTAINS SESSIONS WINNERS ID)
            Cycle cycle = new Cycle();
            for(Cycle c : tontine.getCycles()) {
                if(c.getSessions().contains(s)) { cycle = c; break; }
            }

            // GENERATE WINNER
            Session session1 = generateWinnersAccordingToGainMode(tontine, cycle, s);

            // WHEN ALL MEMBERS ALREADY WON
            if(session1 == null) {
                throw new Exception("All tontine members have already won !");
            }

            // GET USER ACCOUNT
            Account account = findAccountByUserAndAccountType(bearerToken, session1.getWinner().getId(), 2L);

            // IF ACCOUNT NOT FOUND
            if(account == null) {
                throw new Exception("This user doesn't have tontine account !");
            }

            // SET USER ACCOUNT BALANCE
            setBalance(bearerToken, account.getId(), cycle.getLotAmount());

            // SET TONTINE ACCOUNT BALANCE
            Account account1 = setBalance(bearerToken, tontine.getAccount().getId(), -cycle.getLotAmount());
            tontine.setAccount(account1);
            tontineRepository.save(tontine);

            // SAVE AND RETURN
            return ResponseHandler.generateOkResponse("Winner generated for session !", sessionRepository.save(session1));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }





    /* **************************************** OTHERS **************************************** */





    // GENERATE WINNER ACCORDING TO GAIN MODE
    private Session generateWinnersAccordingToGainMode(Tontine tontine, Cycle cycle, Session session) throws Exception {

        // GET GAIN MODE
        GainMode gainMode = tontine.getGainMode();

        // TO RETURN
        Session toReturn = null;

        // GENERATE WINNERS OF EACH SESSION ACCORDING TO GAIN MODE
        switch (gainMode.getLabel()) {

            // ORDRE
            case "ORDRE" : {

                // PROVIDING ORDERED LIST OF MEMBERS ACCORDING TO HOW THEY HAVE BEEN ADDED IN TONTINE
                toReturn = generateWinner(tontine.getTontineMembers(), cycle, session);

            } break;

            // TIRAGE
            case "TIRAGE AU SORT" : {

                // SHUFFLE MEMBERS OF TONTINE
                List<TontineMember> shuffledMembers = new ArrayList<>(tontine.getTontineMembers());
                Collections.shuffle(shuffledMembers);

                // PROVIDING SHUFFLED LIST OF MEMBERS
                toReturn = generateWinner(new HashSet<>(shuffledMembers), cycle, session);

            } break;

            case "ENCHÈRES" : {
                System.out.println("NOT READY !");
            } break;
        }

        return toReturn;
    }


    // GENERATE WINNER
    private Session generateWinner(Set<TontineMember> members, Cycle cycle, Session session) throws Exception {

        // IF SESSION STATUS IS NOT "CLOTURÉ"
        if(!session.getStatus().getLabel().equals("CLOTURÉ")) {
            throw new Exception("Session is still opened or not fully closed ! Please close session before generating winner.");
        }

        // IF SESSION HAS ALREADY A WINNER
        if(session.getWinner() != null) {
            throw new Exception("This session has already a winner !");
        }

        // SESSION TO RETURN
        Session toReturn = null;

        // FOR EACH MEMBERS TEST IF MEMBERS HAS ALREADY BEEN ADDED TO CYCLE SESSION WINNERS
        // ACCORDING TO PLAN
        for(TontineMember tm : members) {

            int numberOfWins = 0; // NUMBER OF WINS

            // COMPUTE NUMBER OF WIN OF MEMBER
            for(Long id : cycle.getSessionWinnersId()) {

                // ADD +1 IN NUMBER OF WINS EACH TIME ID OF MEMBER IS ENCOUNTERED
                if(id.equals(tm.getParticipant().getId())) {
                    numberOfWins++;
                }
            }

            // COMPARE MEMBER NUMBER OF WINS WITH ITS PLAN
            if(numberOfWins < tm.getUserPlan()) {

                // IF NUMBER OF WINS < USER PLAN
                // USER IS WINNER OF THIS SESSION
                session.setWinner(tm.getParticipant());
                // toReturn = sessionRepository.save(session);
                toReturn = session;


                // UPDATE SESSION WINNERS SESSION IN CYCLE
                cycle.getSessionWinnersId().add(tm.getParticipant().getId());
                cycleRepository.save(cycle);

                // A WINNER IS FOUND SO WE CAN STOP
                break;
            }
        }

        // RETURN
        return toReturn;
    }


    // VERIFY IF ALL CONTRIBUTIONS HAVE BEEN PAID
    private Boolean verifyContributions(Session session) {

        // GET ALL SESSION USER PAYMENT STATES
        for(UserPaymentState ps : session.getUserPaymentStates()) {

            // IF ONLY ONE USER ISN'T UP TO DATE, RETURN FALSE. IF THERE'S ONE USER WHO ISN'T UP TO DATE, RETURN FALSE
            if(!ps.getUpToDate()) {
                return false;
            }
        }

        // ELSE, RETURN TRUE. ALL USERS ARE UP TO DATE ON THEIR PAYMENTS
        return true;
    }


    // VERIFY PENALTIES
    /* private Boolean verifyPenalties(Session session) {

        // GET ALL SESSION USER PENALTY STATES
        for (UserPenaltyState ps : session.getUserPenaltyStates()) {

            // IF ONLY ONE USER ISN'T UP TO DATE, RETURN FALSE. IF THERE'S ONE USER WHO ISN'T UP TO DATE, RETURN FALSE
            if(!ps.getUpToDate()) {
                return false;
            }
        }

        // ELSE, RETURN TRUE. ALL USERS ARE UP TO DATE ON THEIR PENALTIES
        return true;
    } */


    // GET ACCOUNT BY USER AND ACCOUNT TYPE
    private Account findAccountByUserAndAccountType(String bearerToken, Long idUser, Long idAccountType) {

        String rAccount = ResponseStringifier.stringifier(feignService.findAccountByUserAndAccountType(bearerToken, idUser, idAccountType).getBody());

        try {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(rAccount, Account.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // SET BALANCE
    private Account setBalance(String bearerToken, Long idAccount, Long amount) {

        String rAccount = ResponseStringifier.stringifier(feignService.setBalance(bearerToken, idAccount, amount).getBody());

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(rAccount, Account.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }
}
