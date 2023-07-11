package com.b2i.activitiesorganisation.service.RiskProfile;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.dto.request.RiskProfile.RiskProfileRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.RiskProfile;
import com.b2i.activitiesorganisation.repository.RiskProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RiskProfileServiceImplementation implements RiskProfileService {


    @Autowired
    private RiskProfileRepository riskProfileRepository;


    // CREATE
    @Override
    public ResponseEntity<Object> createRiskProfile(RiskProfileRequest riskProfileRequest) {

        try {

            // CHECK RISK LEVEL
            Utils.checkLongValues(riskProfileRequest.getRiskLevel(), "Risk profile level");

            // VERIFY IF SIMILAR RISK PROFILE EXISTS
            verifyIfRiskProfileExists(riskProfileRepository.findAll(), riskProfileRequest.getRiskLevel());

            // SAVE NEW RISK PROFILE
            return ResponseHandler.generateCreatedResponse("Risk profile created !",
                    riskProfileRepository.save(new RiskProfile(riskProfileRequest.getRiskLevel())));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllRiskProfiles() {

        // GET ALL
        List<RiskProfile> riskProfiles = riskProfileRepository.findAll();

        try {
            if(riskProfiles.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Risk profile list", riskProfiles);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateRiskProfile(Long idProfile, RiskProfileRequest riskProfileRequest) {

        // GET RISK PROFILE
        Optional<RiskProfile> profile = riskProfileRepository.findById(idProfile);

        try {

            return profile.map(p -> {

                // VERIFY IF SIMILAR RISK PROFILE EXISTS
                try {
                    verifyIfRiskProfileExists(riskProfileRepository.findAll(), riskProfileRequest.getRiskLevel());
                }
                catch (Exception e) {
                    return Utils.catchException(e);
                }

                // UPDATE
                if(!riskProfileRequest.getRiskLevel().equals(0L) && riskProfileRequest.getRiskLevel() != null) {
                    p.setRiskLevel(riskProfileRequest.getRiskLevel());
                }

                // SAVE
                return ResponseHandler.generateOkResponse("Risk profile updated !",
                        riskProfileRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Risk profile not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deleteRiskProfile(Long idProfile) {

        // GET RISK PROFILE
        Optional<RiskProfile> riskProfile = riskProfileRepository.findById(idProfile);

        try {

            if(!riskProfile.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Risk profile not found !");
            }

            riskProfileRepository.deleteById(idProfile);
            return ResponseHandler.generateOkResponse("Risk profile properly deleted !", null);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findRiskProfileById(Long idProfile) {

        // GET RISK PROFILE
        Optional<RiskProfile> riskProfile = riskProfileRepository.findById(idProfile);

        try {
            return riskProfile.map(rp -> ResponseHandler.generateOkResponse("Risk profile " + idProfile, rp))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Risk profile not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // COUNT
    @Override
    public Long countAll() {
        return riskProfileRepository.count();
    }





    /* ======================================== OTHERS ================================================== */





    private void verifyIfRiskProfileExists(List<RiskProfile> riskProfiles, Long riskLevel) throws Exception {

        // VERIFY IF THERE'S NO OTHER RISK PROFILE WITH THE SAME RISK LEVEL
        for(RiskProfile p : riskProfiles) {
            if(p.getRiskLevel().equals(riskLevel)){
                throw new Exception("A similar risk profile already exists !");
            }
        }
    }
}
