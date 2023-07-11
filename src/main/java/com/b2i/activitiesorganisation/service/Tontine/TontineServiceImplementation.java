package com.b2i.activitiesorganisation.service.Tontine;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.Tontine.TontineRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.*;
import com.b2i.activitiesorganisation.repository.*;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TontineServiceImplementation implements TontineService {

    @Autowired
    private TontineRepository tontineRepository;

    @Autowired
    private FrequencyRepository frequencyRepository;

    @Autowired
    private TransversalityLevelRepository levelRepository;

    @Autowired
    private GainModeRepository gainModeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private TontineMemberRepository tontineMemberRepository;

    @Autowired
    private FeignService feignService;

    ObjectMapper mapper = new ObjectMapper();


    // CREATE
/*
    @Override
    public ResponseEntity<Object> createTontine(TontineRequest tontineRequest, Long idClub, Long idTransversalityLevel, Long idContributionFrequency, Long idTontineSessionFrequency, Long idGainMode) {

        try {

            // CLUB
            if (getClub(idClub) == null) {
                return ResponseHandler.generateNotFoundResponse("Club not found !");
            }
            Club club = getClub(idClub);


            // ACCOUNT
            Account account = createAccount();

            // PEB
            if (tontineRequest.getPEB() == null) {
                throw new Exception("Tontine PEB can't be null !");
            }

            // DURATION IN MONTHS
            if (tontineRequest.getDurationInMonths() == null || tontineRequest.getDurationInMonths() == 0L) {
                throw new Exception("Months duration can't be null or equals to 0 !");
            }

            // TONTINE NAME
            Utils.checkStringValues(tontineRequest.getName(), "Tontine name");

            // OBSERVATION
            Utils.checkStringValues(tontineRequest.getObservation(), "Observation");

            // NEW TONTINE
            Tontine tontine = new Tontine(tontineRequest.getName(), tontineRequest.getPEB(), tontineRequest.getDurationInMonths(), tontineRequest.getObservation(), club, account);

            // TRANSVERSAL LEVEL
            if (levelRepository.findById(idTransversalityLevel).isPresent()) {
                tontine.setLevel(levelRepository.findById(idTransversalityLevel).get());
                if (idTransversalityLevel > 1L) {
                    tontine.setTransversal(true);
                }
            } else {
                return ResponseHandler.generateNotFoundResponse("Transversality level not found !");
            }


            // CONTRIBUTION FREQUENCY
            if (frequencyRepository.findById(idContributionFrequency).isPresent()) {
                tontine.setContributionFrequency(frequencyRepository.findById(idContributionFrequency).get());
            } else {
                return ResponseHandler.generateNotFoundResponse("Frequency for contribution frequency not found !");
            }


            // TONTINE SESSION FREQUENCY
            if (frequencyRepository.findById(idTontineSessionFrequency).isPresent()) {
                tontine.setTontineSessionFrequency(frequencyRepository.findById(idTontineSessionFrequency).get());
            } else {
                return ResponseHandler.generateNotFoundResponse("Frequency for tontine session not found !");
            }


            // GAIN MODE
            if (gainModeRepository.findById(idGainMode).isPresent()) {
                tontine.setGainMode(gainModeRepository.findById(idGainMode).get());
            } else {
                return ResponseHandler.generateNotFoundResponse("Gain mode not found !");
            }

            // STATUS
            if (statusRepository.findStatusByLabel("OUVERT").isPresent()) {
                tontine.setStatus(statusRepository.findStatusByLabel("OUVERT").get());
            }

            // SESSIONS NUMBER
            tontine.setSessionsNumber(generateSessionsNumber(tontine.getTontineSessionFrequency(), tontine.getDurationInMonths()));

            // AMOUNT LOT
            tontine.setAwardedLots((tontine.getSessionsNumber() - 1) * tontine.getPEB());

            // SAVE TONTINE
            tontine = tontineRepository.save(tontine);

            return ResponseHandler.generateCreatedResponse("Tontine created", tontine);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }
*/
    @Override
    public ResponseEntity<Object> createTontine(String token, TontineRequest tontineRequest, Long idClub, Long idTransversalityLevel, Long idContributionFrequency, Long idTontineSessionFrequency, Long idGainMode) {
        // GET BEARER TOKEN
        String bearerToken = "Bearer " + token;

        try {

            // CLUB
            if (getClub(bearerToken, idClub) == null) {
                return ResponseHandler.generateNotFoundResponse("Club not found !");
            }
            Club club = getClub(bearerToken, idClub);
            // ACCOUNT
            Account account = createAccount(bearerToken);
            // PEB
            if (tontineRequest.getPEB() == null) {
                throw new Exception("Tontine PEB can't be null !");
            }

            // DURATION IN MONTHS
            if (tontineRequest.getDurationInMonths() == null || tontineRequest.getDurationInMonths() == 0L) {
                throw new Exception("Months duration can't be null or equals to 0 !");
            }

            // TONTINE NAME
            Utils.checkStringValues(tontineRequest.getName(), "Tontine name");

            // OBSERVATION
            Utils.checkStringValues(tontineRequest.getObservation(), "Observation");

            // NEW TONTINE
            Tontine tontine = new Tontine(tontineRequest.getName(), tontineRequest.getPEB(), tontineRequest.getDurationInMonths(), tontineRequest.getObservation(), club, account);
            // TRANSVERSAL LEVEL
            if (levelRepository.findById(idTransversalityLevel).isPresent()) {
                tontine.setLevel(levelRepository.findById(idTransversalityLevel).get());
                if (idTransversalityLevel > 1L) {
                    tontine.setTransversal(true);
                }
            } else {
                return ResponseHandler.generateNotFoundResponse("Transversality level not found !");
            }


            // CONTRIBUTION FREQUENCY
            if (frequencyRepository.findById(idContributionFrequency).isPresent()) {
                tontine.setContributionFrequency(frequencyRepository.findById(idContributionFrequency).get());
            } else {
                return ResponseHandler.generateNotFoundResponse("Frequency for contribution frequency not found !");
            }


            // TONTINE SESSION FREQUENCY
            if (frequencyRepository.findById(idTontineSessionFrequency).isPresent()) {
                tontine.setTontineSessionFrequency(frequencyRepository.findById(idTontineSessionFrequency).get());
            } else {
                return ResponseHandler.generateNotFoundResponse("Frequency for tontine session not found !");
            }


            // GAIN MODE
            if (gainModeRepository.findById(idGainMode).isPresent()) {
                tontine.setGainMode(gainModeRepository.findById(idGainMode).get());
            } else {
                return ResponseHandler.generateNotFoundResponse("Gain mode not found !");
            }

            // STATUS
            if (statusRepository.findStatusByLabel("OUVERT").isPresent()) {
                tontine.setStatus(statusRepository.findStatusByLabel("OUVERT").get());
            }

            // SESSIONS NUMBER
            tontine.setSessionsNumber(generateSessionsNumber(tontine.getTontineSessionFrequency(), tontine.getDurationInMonths()));

            // AMOUNT LOT
            tontine.setAwardedLots((tontine.getSessionsNumber() - 1) * tontine.getPEB());

            System.out.println("================================ tontine::" + tontine);
            // SAVE TONTINE
            tontine = tontineRepository.save(tontine);

            return ResponseHandler.generateCreatedResponse("Tontine created", tontine);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }

    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllTontines() {

        // GET ALL TONTINES
        List<Tontine> tontines = tontineRepository.findAll();

        try {

            if (tontines.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }
            return ResponseHandler.generateOkResponse("Tontines list", tontines);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findTontineById(Long id) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(id);

        try {
            return tontine.map(it -> ResponseHandler.generateOkResponse("Tontine " + id, it))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // DELETE BY ID
/*
    @Override
    public ResponseEntity<Object> deleteTontineById(Long idTontine) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {

            if (!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            }

            String r = ResponseStringifier.getStatus(feignService.deleteAccount(tontine.get().getAccount().getId()).getBody());
            if (!r.equals("OK") && !r.equals("NOT_FOUND")) {
                throw new Exception("Tontine account can't be deleted properly !");
            }

            tontineRepository.deleteById(idTontine);
            return ResponseHandler.generateOkResponse("Tontine " + idTontine + " has been properly deleted !", null);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }
*/
    @Override
    public ResponseEntity<Object> deleteTontineById(String token, Long idTontine) {

        String bearerToken = "Bearer " + token;

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {

            if (!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            }

            String r = ResponseStringifier.getStatus(feignService.deleteAccount(bearerToken, tontine.get().getAccount().getId()).getBody());
            if (!r.equals("OK") && !r.equals("NOT_FOUND")) {
                throw new Exception("Tontine account can't be deleted properly !");
            }

            tontineRepository.deleteById(idTontine);
            return ResponseHandler.generateOkResponse("Tontine " + idTontine + " has been properly deleted !", null);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }



    // UPDATE
    @Override
    public ResponseEntity<Object> updateTontine(TontineRequest tontineRequest, Long idTontine) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {
            return tontine.map(t -> {

                if (tontineRequest.getName() != null) {
                    t.setName(tontineRequest.getName());
                }

                if (tontineRequest.getObservation() != null) {
                    t.setObservation(tontineRequest.getObservation());
                }

                Tontine tontine1 = tontineRepository.save(t);

                return ResponseHandler.generateOkResponse("Tontine " + idTontine + " has been properly updated !", tontine1);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }


    // SET CONTRIBUTION FREQUENCY
    @Override
    public ResponseEntity<Object> setFrequency(Long idTontine, Long idFrequency) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);
        Optional<Frequency> frequency = frequencyRepository.findById(idFrequency);

        try {
            if (!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            } else if (!frequency.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Frequency not found !");
            }

            Tontine t = tontine.get();
            t.setContributionFrequency(frequency.get());
            t = tontineRepository.save(t);

            return ResponseHandler.generateOkResponse("Frequency " + idFrequency
                    + " has properly been added to Tontine " + idTontine + " !", t);

        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }


    // SET TRANSVERSAL LEVEL
    @Override
    public ResponseEntity<Object> setTransversalLevel(Long idTontine, Long idLevel) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        // GET LEVEL
        Optional<TransversalityLevel> level = levelRepository.findById(idLevel);

        try {
            if (!level.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Transversality level not found !");
            }
            TransversalityLevel l = level.get();

            return tontine.map((t) -> {
                t.setLevel(l);
                t.setTransversal(!l.getLabel().equals("CLUB"));
                Tontine tontine1 = tontineRepository.save(t);

                return ResponseHandler.generateOkResponse("Transversality level " + idLevel + " has" +
                        " been properly added to tontine " + idTontine + " !", tontine1);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // ADD PARTICIPANT
   /* @Override
    public ResponseEntity<Object> addParticipant(Long idTontine, Long idUser, Long plan) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        // GET USER
        User user = getUser(idUser);

        try {
            if (!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            }

            Tontine t = tontine.get();

            // CHECK STATUS
            if (t.getStatus().getLabel().equals("FERMÉ")) {
                throw new Exception("This tontine can't get participants anymore!");
            }

            // ADDING USER
            // CHECK TRANSVERSALITY LEVEL

            // IF LEVEL IS CLUB
            if (!t.getTransversal()) {
                List<Long> cUsers = getClubUsers(t.getClubOwner().getId());
                testBeforeAddToTontine(user, t, cUsers, plan);
            }

            // IF LEVEL ISN'T CLUB
            else {

                // GET AREA FOR BOTH CASE WHERE LEVEL IS ZONE OR CENTER
                Area area = getAreaOfClub(t.getClubOwner().getId());

                // LEVEL IS AREA
                if (t.getLevel().getLabel().equals("ZONE")) {
                    // GET AREA USERS
                    List<Long> aUsers = getAreaUsers(area.getId());
                    testBeforeAddToTontine(user, t, aUsers, plan);
                }

                // LEVEL IS CENTER
                else {

                    // GET CENTER
                    Center center = getCenterOfArea(area.getId());

                    // GET CENTER USERS
                    List<Long> cUsers = getCenterUsers(center.getId());
                    testBeforeAddToTontine(user, t, cUsers, plan);
                }
            }

            // SAVE TONTINE
            t = tontineRepository.save(t);

            return ResponseHandler.generateOkResponse("User " + idUser + " has properly been added as participant to" +
                    " tontine " + idTontine, t);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }
*/
    @Override
    public ResponseEntity<Object> addParticipant(String token, Long idTontine, Long idUser, Long plan) {

        String bearerToken = "Bearer " + token;

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        // GET USER
        User user = getUser(bearerToken, idUser);

        try {
            if (!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            }

            Tontine t = tontine.get();

            // CHECK STATUS
            if (t.getStatus().getLabel().equals("FERMÉ")) {
                throw new Exception("This tontine can't get participants anymore!");
            }

            // ADDING USER
            // CHECK TRANSVERSALITY LEVEL

            // IF LEVEL IS CLUB
            if (!t.getTransversal()) {
                List<Long> cUsers = getClubUsers(bearerToken, t.getClubOwner().getId());
                testBeforeAddToTontine(user, t, cUsers, plan);
            }

            // IF LEVEL ISN'T CLUB
            else {

                // GET AREA FOR BOTH CASE WHERE LEVEL IS ZONE OR CENTER
                Area area = getAreaOfClub(bearerToken, t.getClubOwner().getId());

                // LEVEL IS AREA
                if (t.getLevel().getLabel().equals("ZONE")) {
                    // GET AREA USERS
                    List<Long> aUsers = getAreaUsers(bearerToken, area.getId());
                    testBeforeAddToTontine(user, t, aUsers, plan);
                }

                // LEVEL IS CENTER
                else {

                    // GET CENTER
                    Center center = getCenterOfArea(bearerToken, area.getId());

                    // GET CENTER USERS
                    List<Long> cUsers = getCenterUsers(bearerToken, center.getId());
                    testBeforeAddToTontine(user, t, cUsers, plan);
                }
            }

            // SAVE TONTINE
            t = tontineRepository.save(t);

            return ResponseHandler.generateOkResponse("User " + idUser + " has properly been added as participant to" +
                    " tontine " + idTontine, t);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // REMOVE PARTICIPANT
    @Override
    public ResponseEntity<Object> removeParticipant(Long idTontine, Long idUser) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);
        try {

            return tontine.map((t) -> {

                // IF USER IS A PARTICIPANT
                for (TontineMember tm : t.getTontineMembers()) {
                    if (tm.getParticipant().getId().equals(idUser)) {
                        t.getTontineMembers().remove(tm);
                        t.setRegisteredMembers((long) t.getTontineMembers().size());
                        break;
                    }
                }

                // SAVING
                t = tontineRepository.save(t);

                return ResponseHandler.generateOkResponse("User " + idUser + " has properly been removed as participant !", t);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // SET GAIN MODE
    @Override
    public ResponseEntity<Object> setGainMode(Long idTontine, Long idGainMode) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        // GET GAIN MODE
        Optional<GainMode> gainMode = gainModeRepository.findById(idGainMode);

        try {

            if (!tontine.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Tontine not found !");
            } else if (!gainMode.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Gain mode not found !");
            }

            Tontine t = tontine.get();
            t.setGainMode(gainMode.get());
            t = tontineRepository.save(t);

            return ResponseHandler.generateOkResponse("Gain mode " + idGainMode
                    + " has properly been added to Tontine " + idTontine + " !", t);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // GET TONTINE USERS
    @Override
    public ResponseEntity<Object> getTontineUsers(Long idTontine) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {

            return tontine.map((t) -> {

                // GET USERS
                List<User> tontineUsers = new ArrayList<>();

                // IF TONTINE DOESN'T HAVE USERS
                if (t.getTontineMembers().isEmpty()) {
                    return ResponseHandler.generateNoContentResponse("No tontine users !");
                }

                // ELSE
                for (TontineMember tm : t.getTontineMembers()) {
                    tontineUsers.add(tm.getParticipant());
                }

                return ResponseHandler.generateOkResponse("Tontine " + idTontine + " users", tontineUsers);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // GET ALL CYCLES
    @Override
    public ResponseEntity<Object> findAllCyclesOfTontine(Long idTontine) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        try {

            return tontine.map((t) -> {
                if (t.getCycles().isEmpty()) {
                    return ResponseHandler.generateNoContentResponse("Cycle list is empty !");
                }

                return ResponseHandler.generateOkResponse("Cycle list", t.getCycles());
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // SET STATUS
    @Override
    public ResponseEntity<Object> setStatus(Long idTontine, Long idStatus) {

        // GET TONTINE
        Optional<Tontine> tontine = tontineRepository.findById(idTontine);

        // GET STATUS
        Optional<Status> status = statusRepository.findById(idStatus);

        try {

            return tontine.map((t) -> {

                if (!status.isPresent()) {
                    return ResponseHandler.generateNotFoundResponse("Status not found !");
                } else {
                    t.setStatus(status.get());
                }

                t = tontineRepository.save(t);

                return ResponseHandler.generateOkResponse("Status properly set to tontine !", t);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Tontine not found !"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // GET T OF A SESSION
    @Override
    public Tontine getTontineOfASession(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            if (!session.isPresent()) {
                throw new Exception("Session not found !");
            }

            Session s = session.get();


            // GET CYCLE OF SESSION
            List<Cycle> cycles = cycleRepository.findAll();
            Cycle cycle = new Cycle();
            for (Cycle c : cycles) {
                if (c.getSessions().contains(s)) {
                    cycle = c;
                    break;
                }
            }

            // GET TONTINE OF CYCLE
            List<Tontine> tontines = tontineRepository.findAll();
            Tontine tontine = new Tontine();
            for (Tontine t : tontines) {
                if (t.getCycles().contains(cycle)) {
                    tontine = t;
                    break;
                }
            }

            System.out.println(tontine);

            // RETURN
            return tontine;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    //GET TONTINES BY NAME
    @Override
    public ResponseEntity<Object> findTontineByName(String name) {
        // GET ALL TONTINES
        List<Tontine> tontines = tontineRepository.findTontineByName(name);

        try {

            if (tontines.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }
            return ResponseHandler.generateOkResponse("Tontines list", tontines);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }





    /* ****************************** OTHERS ****************************** */





    // GET CLUB
    private Club getClub(String bearerToken, Long idClub) {

        Club c = new Club();
        String rClub = ResponseStringifier.stringifier(feignService.findClubById(bearerToken, idClub).getBody());
        if (!rClub.equals("")) {
            try {
                c = mapper.readValue(rClub, Club.class);
            } catch (Exception e) {
                e.printStackTrace();
                c = null;
            }
        }

        return c;
    }


    // GET USER
    private User getUser(String bearerToken, Long id) {

        User u = new User();
        String rUser = ResponseStringifier.stringifier(feignService.getUserById(bearerToken, id).getBody());
        if (!rUser.equals("")) {
            try {
                u = mapper.readValue(rUser, User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            u = null;
        }
        return u;
    }


    // CREATE ACCOUNT
    private Account createAccount(String bearerToken) {

        Account a = new Account();
        String rAccount = ResponseStringifier.stringifier(feignService.createAccount(bearerToken, 2L).getBody());
        if (!rAccount.equals("")) {
            try {
                a = mapper.readValue(rAccount, Account.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            a = null;
        }
        return a;
    }


    // GET AREA OF CLUB
    private Area getAreaOfClub(String bearerToken,Long id) {

        Area area = new Area();
        String rArea = ResponseStringifier.stringifier(feignService.getAreaOfClub(bearerToken, id).getBody());
        if (!rArea.equals("")) {
            try {
                area = mapper.readValue(rArea, Area.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                area = null;
            }
        }
        return area;
    }


    // GET CLUB USER
    private List<Long> getClubUsers(String bearerToken, Long id) {

        List<Long> clubUsers = new ArrayList<>();
        String rClubUsers = ResponseStringifier.stringifier(feignService.getAllClubUsers(bearerToken, id).getBody());
        if (!rClubUsers.equals("")) {
            try {
                clubUsers = mapper.readValue(rClubUsers, List.class);
            } catch (Exception e) {
                e.printStackTrace();
                clubUsers = null;
            }
        }
        return clubUsers;
    }


    // GET AREA USER
    private List<Long> getAreaUsers(String bearerToken, Long id) {

        List<Long> areaUsers = new ArrayList<>();
        String rAreaUsers = ResponseStringifier.stringifier(feignService.getAllAreaUsers(bearerToken, id).getBody());
        if (!rAreaUsers.equals("")) {
            try {
                areaUsers = mapper.readValue(rAreaUsers, List.class);
            } catch (Exception e) {
                e.printStackTrace();
                areaUsers = null;
            }
        }
        return areaUsers;
    }


    // GET CENTER OF AREA
    private Center getCenterOfArea(String bearerToken, Long id) {

        Center center = new Center();
        String rCenter = ResponseStringifier.stringifier(feignService.getCenterOfArea(bearerToken, id).getBody());
        if (!rCenter.equals("")) {
            try {
                center = mapper.readValue(rCenter, Center.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                center = null;
            }
        }
        return center;
    }


    // GET CENTER USER
    private List<Long> getCenterUsers(String bearerToken, Long id) {

        List<Long> centerUsers = new ArrayList<>();
        String rCenterUsers = ResponseStringifier.stringifier(feignService.getAllCenterUsers(bearerToken,id).getBody());
        if (!rCenterUsers.equals("")) {
            try {
                centerUsers = mapper.readValue(rCenterUsers, List.class);
            } catch (Exception e) {
                e.printStackTrace();
                centerUsers = null;
            }
        }
        return centerUsers;
    }


    // TEST IF USER CAN BE ADDED TO TONTINE OR NOT
    private void testBeforeAddToTontine(User participant, Tontine t, List<Long> usersId, Long userPlan) throws Exception {

        // USER IS NOT IN CLUB OR AREA OR CENTER OF CLUB OWNER
        if (!usersId.contains((double) participant.getId())) {
            throw new Exception("User " + participant.getId() + " can't be participant of tontine " + t.getId() + " !");
        }

        // USER CAN BE ADDED SO WE MAKE A TEST ON IT
        boolean test = false;
        for (TontineMember tm : t.getTontineMembers()) {
            if (tm.getParticipant().getId().equals(participant.getId())) {
                test = true;
                break;
            }
        }

        // IF TEST == TRUE, USER IS ALREADY A PARTICIPANT
        if (test) {
            throw new Exception("User is already a participant of tontine " + t.getId() + " !");
        } else {

            // SESSION NUMBER CAN'T BE LOWER THAN NUMBER OF "BRAS"
            Long handsNumberInTontine = 0L;
            for(TontineMember tm : t.getTontineMembers()) {
                handsNumberInTontine += tm.getUserPlan(); // COMPUTE "BRAS" IN THIS TONTINE
            }

            // IF USER PLAN + NUMBER OF "BRAS" IN THIS TONTINE > SESSIONS NUMBER, USER CAN'T BE ADDED
            if((handsNumberInTontine + userPlan) > t.getSessionsNumber()) {
                throw new Exception("User can't be added in this tontine ! Number of hands is greater than sessions number."
                        + " Only " + ((handsNumberInTontine + userPlan) - t.getSessionsNumber()) + " hands remaining !");
            }

            // ELSE
            TontineMember tontineMember = tontineMemberRepository.save(new TontineMember(t.getName(), participant, userPlan));
            t.getTontineMembers().add(tontineMember);
            t.setRegisteredMembers((long) t.getTontineMembers().size());
        }
    }


    // GENERATE SESSIONS NUMBER
    private Long generateSessionsNumber(Frequency f, Long duration) throws Exception {

        // START DATE EXAMPLE
        LocalDate startDate = LocalDate.now();

        // END DATE EXAMPLE
        LocalDate endDate = startDate.plusMonths(duration);

        // SESSION NUMBER
        long sessionNumber;

        // CALCULATING SESSION NUMBER
        switch (f.getLabel()) {

            // HEBDOMADAIRE
            case "HEBDOMADAIRE": {

                // GET THE NUMBER OF WEEKS BETWEEN END DATE AND START DATE (OF THE CYCLE)
                sessionNumber = ChronoUnit.WEEKS.between(startDate, endDate);
            }
            break;

            // MENSUELLE
            case "MENSUELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                sessionNumber = ChronoUnit.MONTHS.between(startDate, endDate);
            }
            break;

            // BIMENSUELLE
            case "BIMENSUELLE": {

                // GET THE NUMBER OF WEEKS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
//                long biWeeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);
//                sessionNumber = (biWeeksBetween / 2);

                long biMonthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
                sessionNumber = (biMonthsBetween / 2);
            }
            break;

            // TRIMESTRIELLE
            case "TRIMESTRIELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                long triMonthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
                sessionNumber = (triMonthsBetween / 3);
            }
            break;

            // SEMESTRIELLE
            case "SEMESTRIELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                long sixMonthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
                sessionNumber = (sixMonthsBetween / 6);
            }
            break;

            // ANNUELLE
            case "ANNUELLE": {

                // GET THE NUMBER OF MONTHS BETWEEN END DATE AND START DATE OF THE WEEK (OF THE CYCLE)
                sessionNumber = ChronoUnit.YEARS.between(startDate, endDate);
            }
            break;

            default: {
                throw new Exception("Frequency not found !");
            }
        }

        return sessionNumber;
    }
}
