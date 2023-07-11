package com.b2i.activitiesorganisation.service.Cycle;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.Cycle.CycleRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.User;
import com.b2i.activitiesorganisation.repository.*;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CycleServiceImplementation implements CycleService {


    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private TontineRepository tontineRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserPaymentStateRepository userPaymentStateRepository;

    @Autowired
    private UserPenaltyStateRepository userPenaltyStateRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private FeignService feignService;

    ObjectMapper mapper = new ObjectMapper();


    // CREATE CYCLE
    @Override
    public ResponseEntity<Object> createCycle(CycleRequest cycleRequest, Long idTontine) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {

            // TESTS
            // TONTINE
            if(!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            }

            // CHECK STATUS
            // THROW NEW EXCEPTION IF STATUS LABEL IS OUVERT (OPEN)
            if(tontine.get().getStatus().getLabel().equals("OUVERT")) {
                throw new Exception("Cycle can't be launched if tontine is still opened !");
            }

            // CHECK IF ANOTHER CYCLE IS OPEN
            boolean anotherCycleIsOpen = false;
            for(Cycle c : tontine.get().getCycles()) {
                if(c.getStatus().getLabel().equals("OUVERT")) { anotherCycleIsOpen = true; break; }
            }

            // AVOID CREATING A CYCLE IN A TONTINE IF A CYCLE IS STILL OPENED
            if(anotherCycleIsOpen) { throw new Exception("Another cycle is still opened for this tontine !"); }

            // NAME
            Utils.checkStringValues(cycleRequest.getName(),"Cycle name" );

            // START DATE
            if(cycleRequest.getStartDate() == null) { throw new Exception("Start date can't be null !"); }


            // NEW CYCLE
            Cycle cycle = new Cycle();

            cycle.setName(cycleRequest.getName()); // NAME
            cycle.setStartDate(cycleRequest.getStartDate()); // START DATE

            // END DATE
            cycle.setEndDate(cycleRequest.getStartDate().plusMonths(tontine.get().getDurationInMonths()));

            // LOT AMOUNT
            // ITERATING USER PLANS (MAP) WITH FOR LOOP
            Long echeanceNumber = 0L;

            if(tontine.get().getTontineMembers().isEmpty()) {
                throw new Exception("This tontine doesn't have any members !");
            }

            for(TontineMember tm : tontine.get().getTontineMembers()) {
                echeanceNumber += tm.getUserPlan();
            }
            cycle.setEcheanceNumber(echeanceNumber);

            // GENERATE LOT AMOUNT
            cycle.setLotAmount((tontine.get().getSessionsNumber() - 1) * tontine.get().getPEB());

            // GENERATE SESSIONS
            generateSessions(cycleRequest.getStartDate(), cycle.getEndDate(), cycle.getSessions(), tontine.get().getTontineSessionFrequency(),
                    cycle.getEcheanceNumber(), tontine.get().getPEB());

            // GENERATE PAYMENT AND PENALTY STATES OF EACH USER FOR EACH SESSION
            cycle.getSessions().forEach(s -> generatePaymentAndPenaltyStates(tontine.get(), s));

            // STATUS
            if(statusRepository.findStatusByLabel("OUVERT").isPresent()) {
                cycle.setStatus(statusRepository.findStatusByLabel("OUVERT").get());
            }

            // NUMBER OF REMAINING SESSIONS
            cycle.setNumberOfSessionRemaining((long) cycle.getSessions().size());

            //SAVE
            cycle = cycleRepository.save(cycle);

            // ADDING CYCLE TO TONTINE
            tontine.get().getCycles().add(cycle);
            tontineRepository.save(tontine.get());

            // SAVE
            return ResponseHandler.generateCreatedResponse("Cycle created", cycle);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND ALL CYCLES
    @Override
    public ResponseEntity<Object> findAllCycles() {

        // GET ALL CYCLES
        List<Cycle> cycles = cycleRepository.findAll();

        try {
            if(cycles.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Cycle list is empty !");
            }

            return ResponseHandler.generateOkResponse("Cycle list", cycles);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // UPDATE CYCLE
    @Override
    public ResponseEntity<Object> updateCycle(CycleRequest cycleRequest, Long idCycle) {

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        try {
            return cycle.map((c) -> {

                if(cycleRequest.getName() != null) {
                    c.setName(cycleRequest.getName());
                }

                return ResponseHandler.generateOkResponse("Cycle " + idCycle + " has been properly updated !",
                        cycleRepository.save(c));
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Cycle not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE CYCLE
    @Override
    public ResponseEntity<Object> deleteCycleById(Long idCycle, Long idTontine) {

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {
            if(!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            }

            if(!cycle.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Cycle not found !");
            }

            // REMOVE CYCLE FROM TONTINE
            Tontine t = tontine.get();
            t.getCycles().remove(cycle.get());
            tontineRepository.save(t);

            // DELETE CYCLE
            cycleRepository.deleteById(idCycle);
            return ResponseHandler.generateOkResponse("Cycle " + idCycle + " has properly been deleted !", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND CYCLE BY ID
    @Override
    public ResponseEntity<Object> findCycleById(Long idCycle) {

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        try {
            return cycle.map((c) -> ResponseHandler.generateOkResponse("Cycle " + idCycle, c))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Cycle not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL SESSIONS OF CYCLE
    @Override
    public ResponseEntity<Object> findAllSessionsOfCycle(Long idCycle) {

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        try {

            return cycle.map((c) -> {
                if(c.getSessions().isEmpty()) {
                    return ResponseHandler.generateNoContentResponse("Cycle doesn't have sessions !");
                }

                return ResponseHandler.generateOkResponse("Cycle sessions", c.getSessions());
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Cycle not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // COUNT ALL CYCLES
    @Override
    public Long countAll() {
        return cycleRepository.count();
    }


    // CLOSE CYCLE
    @Override
    public ResponseEntity<Object> closeCycleById(Long idCycle) {

        // GET CYCLE
        Optional<Cycle> cycle = cycleRepository.findById(idCycle);

        try {

            return cycle.map(c -> {

                // VERIFY SESSIONS
                if(!verifySessions(c)) {
                    return ResponseHandler.generateError(new Exception("Sessions are not all closed yet !"));
                }

                // CLOSE CYCLE
                if(statusRepository.findStatusByLabel("CLOTURÉ").isPresent()) {
                    c.setStatus(statusRepository.findStatusByLabel("CLOTURÉ").get());
                }

                return ResponseHandler.generateOkResponse("Cycle has properly been closed !",
                        cycleRepository.save(c));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Cycle not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> findCycleByName(String name) {

        try {
            List<Cycle> cycles = cycleRepository.findCycleByName(name);
            if (cycles.isEmpty()) {
                return ResponseHandler.generateResponse("Cycles list", HttpStatus.NO_CONTENT, null);
            }

            return ResponseHandler.generateResponse("Cycles list", HttpStatus.OK, cycles);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }






    /* **************************************** OTHERS **************************************** */





    // GENERATE SESSION ACCORDING TO FREQUENCY
    private void generateSessions(LocalDate startDate, LocalDate endDate, List<Session> sessionSet, Frequency frequency, Long echeanceNumber, Long peb) throws Exception {

        // DEFAULT HOUR
        LocalTime defaultHour = LocalTime.now().withSecond(0).withNano(0);

        // GENERATE SESSIONS ACCORDING TO FREQUENCY
        switch (frequency.getLabel()) {

            // HEBDOMADAIRE
            case "HEBDOMADAIRE": {

                // GET THE NUMBER OF WEEKS BETWEEN END DATE AND START DATE (OF THE CYCLE)
                Long weeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);


                int i = 0;
                while ((i < weeksBetween)) {

                    // CREATE SESSION FOR EVERY WEEKS
                    // ADDING A WEEK TO START DATE UNTIL END DATE
                    Session session = new Session(startDate.plusWeeks(i+1), defaultHour, startDate.plusWeeks(i+1));
                    sessionSet.add(sessionRepository.save(session));
                    i++;
                }
            } break;

            // MENSUELLE
            case "MENSUELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                Long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);

                int i = 0;
                while ((i < monthsBetween)) {

                    // CREATE SESSION FOR EVERY MONTHS
                    // ADDING A MONTH TO START DATE UNTIL END DATE
                    Session session = new Session(startDate.plusMonths(i+1), defaultHour, startDate.plusMonths(i+1));
                    sessionSet.add(sessionRepository.save(session));
                    i++;
                }
            } break;

            // BIMENSUELLE
            case "BIMENSUELLE": {

                // GET THE NUMBER OF WEEKS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                Long biWeeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);

                int i = 0;
                while ((i < biWeeksBetween)) {

                    // CREATE SESSION FOR EVERY TWO WEEKS
                    // ADDING TWO WEEKS TO START DATE UNTIL END DATE
                    Session session = new Session(startDate.plusWeeks(i+2), defaultHour, startDate.plusWeeks(i+2));
                    sessionSet.add(sessionRepository.save(session));
                    i += 2;
                }
            } break;

            // TRIMESTRIELLE
            case "TRIMESTRIELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                Long triMonthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);

                int i = 0;
                while ((i < triMonthsBetween)) {

                    // CREATE SESSION FOR EVERY THREE MONTHS
                    // ADDING THREE MONTHS TO START DATE UNTIL END DATE
                    Session session = new Session(startDate.plusMonths(i+3), defaultHour, startDate.plusMonths(i+3));
                    sessionSet.add(sessionRepository.save(session));
                    i += 3;
                }
            } break;

            // SEMESTRIELLE
            case "SEMESTRIELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                Long sixMonthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);

                int i = 0;
                while ((i < sixMonthsBetween)) {

                    // CREATE SESSION FOR EVERY SIX MONTHS
                    // ADDING SIX MONTHS TO START DATE UNTIL END DATE
                    Session session = new Session(startDate.plusMonths(i+6), defaultHour, startDate.plusMonths(i+6));
                    sessionSet.add(sessionRepository.save(session));
                    i += 6;
                }
            } break;

            // ANNUELLE
            case "ANNUELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                Long yearBetween = ChronoUnit.YEARS.between(startDate, endDate);

                int i = 0;
                while ((i < yearBetween)) {

                    // CREATE SESSION FOR EVERY YEAR
                    // ADDING A YEAR TO START DATE UNTIL END DATE
                    Session session = new Session(startDate.plusYears(i+1), defaultHour, startDate.plusMonths(i+1));
                    sessionSet.add(sessionRepository.save(session));
                    i++;
                }
            } break;

            default: {
                throw new Exception("Frequency not found !");
            }
        }

        // GENERATE LOT SIZE FOR EACH SESSION
        for(Session s : sessionSet) {
            s.setTotalToBePaid(echeanceNumber * peb);
            if(statusRepository.findStatusByLabel("FERMÉ").isPresent()) {
                s.setStatus(statusRepository.findStatusByLabel("FERMÉ").get());
            }
            sessionRepository.save(s);
        }
    }


    // GENERATE PAYMENT AND PENALTY STATES
    private void generatePaymentAndPenaltyStates(Tontine tontine, Session session) {

        try {

            for(TontineMember tm : tontine.getTontineMembers()) {

                // GET USER
                User user = tm.getParticipant();

                /*
                 * PAYMENT STATE
                 */
                // CREATING PAYMENT STATE FOR EACH USER OF THE TONTINE
                UserPaymentState userPaymentState = new UserPaymentState();
                userPaymentState.setUserId(user.getId()); // SET USER ID
                userPaymentState.setUserName(user.getFirstName() + " " + user.getLastName()); // SET USER NAME

                // COMPUTE WHAT USER HAVE TO PAY : PLAN * PEB
                userPaymentState.setToBePaidByUser(tm.getUserPlan() * tontine.getPEB());
                userPaymentState.setRemainingToPayByUser(userPaymentState.getToBePaidByUser());

                // SAVING
                userPaymentState = userPaymentStateRepository.save(userPaymentState);
                session.getUserPaymentStates().add(userPaymentState);


                /*
                 * PENALTY STATE
                 */
                // CREATING PENALTY STATE FOR EACH USER OF THE TONTINE
                UserPenaltyState userPenaltyState = new UserPenaltyState();
                userPenaltyState.setUserId(user.getId()); // SET USER ID
                userPenaltyState.setUserName(user.getFirstName() + " " + user.getLastName()); // SET USER NAME

                // SAVING
                userPenaltyState = userPenaltyStateRepository.save(userPenaltyState);
                session.getUserPenaltyStates().add(userPenaltyState);
            }

            // SAVING SESSION
            sessionRepository.save(session);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
        }
    }


    // VERIFY IF ALL SESSIONS OF CYCLE ARE CLOSED
    private Boolean verifySessions(Cycle cycle) {

        // VERIFY IF ALL SESSIONS OF CYCLE ARE CLOSED
        for(Session s : cycle.getSessions()) {

            // IF ONLY ONE SESSION IS STILL OPENED, RETURN FALSE
            if(s.getStatus().getLabel().equals("OUVERT"))  {
                return false;
            }
        }

        // ELSE RETURN TRUE. ALL SESSIONS ARE CLOSED
        return true;
    }
}
