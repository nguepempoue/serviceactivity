package com.b2i.activitiesorganisation.service.RiskProfile;

import com.b2i.activitiesorganisation.dto.request.RiskProfile.RiskProfileRequest;
import org.springframework.http.ResponseEntity;

public interface RiskProfileService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createRiskProfile(RiskProfileRequest riskProfileRequest);

    ResponseEntity<Object> findAllRiskProfiles();

    ResponseEntity<Object> updateRiskProfile(Long idProfile, RiskProfileRequest riskProfileRequest);

    ResponseEntity<Object> deleteRiskProfile(Long idProfile);


    // MORE OPERATIONS //
    ResponseEntity<Object> findRiskProfileById(Long idProfile);

    Long countAll();
}
