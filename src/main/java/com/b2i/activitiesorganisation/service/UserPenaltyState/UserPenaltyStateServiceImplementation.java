package com.b2i.activitiesorganisation.service.UserPenaltyState;

import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.Session;
import com.b2i.activitiesorganisation.model.UserPenaltyState;
import com.b2i.activitiesorganisation.repository.SessionRepository;
import com.b2i.activitiesorganisation.repository.UserPenaltyStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPenaltyStateServiceImplementation implements UserPenaltyStateService {

    @Autowired
    private UserPenaltyStateRepository userPenaltyStateRepository;

    @Autowired
    private SessionRepository sessionRepository;


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllUserPenaltyState() {

        List<UserPenaltyState> userPenaltyStateList = userPenaltyStateRepository.findAll();

        try {
            if(userPenaltyStateList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Penalty states list", userPenaltyStateList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findUserPenaltyStateById(Long idUserPenaltyState) {

        // GET PENALTY STATE
        Optional<UserPenaltyState> userPenaltyState = userPenaltyStateRepository.findById(idUserPenaltyState);

        try {

            return userPenaltyState.map(ups -> ResponseHandler.generateOkResponse("Penalty state " + idUserPenaltyState, ups))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Penalty state not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND BY USER ID AND SESSION
    @Override
    public ResponseEntity<Object> findUserPenaltyStateByUserIdAndSession(Long idUser, Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            if(!session.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Session not found !");
            }

            Session s = session.get();

            UserPenaltyState ups = null;

            // SEARCHING FOR USER PENALTY STATE OF USER (ID USER)
            for(UserPenaltyState userPenaltyState : s.getUserPenaltyStates()) {

                if(userPenaltyState.getUserId().equals(idUser))
                {
                    ups = userPenaltyState;
                    break;
                }
            }

            // RETURN
            if(ups == null) {
                return ResponseHandler.generateNotFoundResponse("User penalty state for this user not found !");
            }

            return ResponseHandler.generateOkResponse("Penalty state", ups);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }


    // FIND ALL USER PENALTY STATES BY SESSION
    @Override
    public ResponseEntity<Object> findAllUserPenaltyStateBySession(Long idSession) {

        // GET SESSION
        Optional<Session> session = sessionRepository.findById(idSession);

        try {

            return session.map(s -> {

                if(s.getUserPenaltyStates().isEmpty()) {
                    return ResponseHandler.generateNoContentResponse("Empty list !");
                }
                return ResponseHandler.generateOkResponse("Penalty states of session" + idSession,
                        s.getUserPenaltyStates());

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Session not found !"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateError(e);
        }
    }
}
