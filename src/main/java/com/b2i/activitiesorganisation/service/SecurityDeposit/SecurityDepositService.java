package com.b2i.activitiesorganisation.service.SecurityDeposit;

import com.b2i.activitiesorganisation.dto.request.SecurityDeposit.SecurityDepositRequest;
import com.b2i.activitiesorganisation.model.SecurityDeposit;
import org.springframework.http.ResponseEntity;

public interface SecurityDepositService {

    // CRUD OPERATIONS
    SecurityDeposit createSecurityDeposit(String token, Long idUser, SecurityDepositRequest securityDepositRequest);

    ResponseEntity<Object> findAll();

    ResponseEntity<Object> updateSecurityDeposit(Long idDeposit, SecurityDepositRequest securityDepositRequest);

    ResponseEntity<Object> deleteSecurityDeposit(Long idMutualInvestment, Long idDeposit);


    // MORE OPERATIONS
    ResponseEntity<Object> findSecurityDepositById(Long idDeposit);

    ResponseEntity<Object> findSecurityDepositByUserAndMutualInvestment(Long idUser, Long idMutualInvestment);
}
