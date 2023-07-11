package com.b2i.activitiesorganisation.service.SecurityDeposit;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.constant.MutualInvesmentEnum;
import com.b2i.activitiesorganisation.dto.request.SecurityDeposit.SecurityDepositRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.MutualInvestment;
import com.b2i.activitiesorganisation.model.SecurityDeposit;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.model.feignEntities.User;
import com.b2i.activitiesorganisation.repository.MutualInvestmentRepository;
import com.b2i.activitiesorganisation.repository.SecurityDepositRepository;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityDepositServiceImplementation implements SecurityDepositService {

    @Autowired
    private FeignService feignService;

    @Autowired
    private MutualInvestmentRepository mutualInvestmentRepository;

    @Autowired
    private SecurityDepositRepository depositRepository;

    ObjectMapper mapper = new ObjectMapper();


    // CREATE
    @Override
    public SecurityDeposit createSecurityDeposit(String token, Long idUser, SecurityDepositRequest securityDepositRequest) {

        String bearerToken = "Bearer " + token;

        try {

            // GET USER
            User user = getUser(bearerToken, idUser);

            // NEW SECURITY DEPOSIT
            SecurityDeposit deposit = new SecurityDeposit(securityDepositRequest.getAmount());

            deposit.setDepositUser(user); // SET USER FOR THIS DEPOSIT DEPOSIT

            // SAVE
            return depositRepository.save(deposit);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    // FIND ALL
    @Override
    public ResponseEntity<Object> findAll() {

        // GET ALL
        List<SecurityDeposit> securityDepositList = depositRepository.findAll();

        try {

            // IF EMPTY
            if(securityDepositList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            // SAVE
            return ResponseHandler.generateOkResponse("Security deposit list", securityDepositList);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateSecurityDeposit(Long idDeposit, SecurityDepositRequest securityDepositRequest) {
        return null;
    }


    // DELETE
    @Override
    public ResponseEntity<Object> deleteSecurityDeposit(Long idMutualInvestment, Long idDeposit) {

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idMutualInvestment);

        // GET SECURITY DEPOSIT
        Optional<SecurityDeposit> deposit = depositRepository.findById(idDeposit);

        try {

            return investment.map(i -> {

                if (i.getStatus() == MutualInvesmentEnum.CLOSE) {
                    return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
                }

                // DEPOSIT NOT FOUND
                if(!deposit.isPresent()) {
                    return ResponseHandler.generateNotFoundResponse("Deposit not found !");
                }

                i.getSecurityDeposits().remove(deposit.get()); // REMOVE DEPOSIT FROM INVESTMENT

                try {
                    depositRepository.deleteById(idDeposit); // DELETE DEPOSIT
                } catch (Exception e) {
                    return Utils.catchException(e);
                }

                // RETURN
                return ResponseHandler.generateOkResponse("Security deposit properly deleted !", null);

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findSecurityDepositById(Long idDeposit) {

        // GET SECURITY DEPOSIT BY ID
        Optional<SecurityDeposit> deposit = depositRepository.findById(idDeposit);

        try {

            return deposit.map(d -> ResponseHandler.generateOkResponse("Security deposit " + idDeposit, d))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Security deposit not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY USER AND MUTUAL INVESTMENT
    @Override
    public ResponseEntity<Object> findSecurityDepositByUserAndMutualInvestment(Long idUser, Long idMutualInvestment) {

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idMutualInvestment);

        try {

            return investment.map(i -> {

                // GET SECURITY DEPOSIT
                SecurityDeposit deposit = null;
                for(SecurityDeposit sd : i.getSecurityDeposits()) {

                    if(sd.getDepositUser().getId().equals(idUser)) {
                        deposit = sd;
                        break;
                    }
                }

                if(deposit != null) {
                    return ResponseHandler.generateOkResponse("Security deposit of user " + idUser, deposit);
                }
                else {
                    return ResponseHandler.generateNotFoundResponse("Security deposit not found !");
                }

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }





    /* ============================== OTHERS ============================== */





    // GET USER
    private User getUser(String bearerToken, Long id) {

        String rUser = ResponseStringifier.stringifier(feignService.getUserById(bearerToken, id).getBody());
        try {
            if(rUser.equals("")) {
                throw new Exception("User not found !");
            }
            return mapper.readValue(rUser, User.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


    // GET ACCOUNT OF USER
    private Account getAccountOfUser(String bearerToken, Long idUser, Long idAccountType) {

        String rAccount = ResponseStringifier.stringifier(feignService.findAccountByUserAndAccountType(bearerToken, idUser, idAccountType).getBody());
        try {
            if(rAccount.equals("")) {
                throw new Exception("Account not found !");
            }
            return mapper.readValue(rAccount, Account.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }
}
