package com.b2i.activitiesorganisation.service.MutualInvestment;

import com.b2i.activitiesorganisation.dto.request.AllocationKey.AllocationKeyRequest;
import com.b2i.activitiesorganisation.dto.request.AmountCollected.AmountCollectedRequest;
import com.b2i.activitiesorganisation.dto.request.MutualInvestment.DistributionPercentageRequest;
import com.b2i.activitiesorganisation.dto.request.MutualInvestment.MutualInvestmentRequest;
import com.b2i.activitiesorganisation.dto.request.Refund.RefundRequest;
import com.b2i.activitiesorganisation.dto.request.SecurityDeposit.SecurityDepositRequest;
import com.b2i.activitiesorganisation.model.ClosingDate;
import com.b2i.activitiesorganisation.model.FirstRefundDate;
import org.springframework.http.ResponseEntity;

public interface MutualInvestmentService {

    // CRUD OPERATIONS //
    ResponseEntity<Object> createMutualInvestment(String token, MutualInvestmentRequest mutualInvestmentRequest, Long idDraweeForm, Long idMutualist, Long idRefundType, Long idFrequency, Long idProfitabilityType,Long idCenter);

    ResponseEntity<Object> findAllMutualInvestments();

    ResponseEntity<Object> updateMutualInvestment(Long idInvestment, MutualInvestmentRequest mutualInvestmentRequest);

    ResponseEntity<Object> deleteMutualInvestment(String token, Long idInvestment);


    // MORE OPERATIONS //
    ResponseEntity<Object> findMutualInvestmentById(Long idInvestment);

    ResponseEntity<Object> generateRefundDates(Long idInvestment, FirstRefundDate firstRefundDate);

    ResponseEntity<Object> setRefundDatesManually(Long idInvestment, RefundRequest refundRequest);

    ResponseEntity<Object> updateInvestmentAllocationKey(Long idInvestment, Long idAllocationKey, AllocationKeyRequest allocationKeyRequest);

    ResponseEntity<Object> addSecurityDeposit(String token, Long idInvestment, Long idUser, SecurityDepositRequest securityDepositRequest);

    // RELEASE OPERATION
    ResponseEntity<Object> releaseOperation(String token, Long idInvestment);

    //REIMBURSEMENT OF AN AMOUNT
    ResponseEntity<Object> refundOfAmountsCollected(String token, Long idRefund, Long idPaymentMethod, AmountCollectedRequest amountCollectedRequest);

    ResponseEntity<Object> makeDistribution(String token, Long idMutualInvesment);

    ResponseEntity<Object> getDistributionPercentage(Long idMutualInvesment, DistributionPercentageRequest distributionPercentageRequest);

    ResponseEntity<Object> findMutualInvestmentByName(String name);

    ResponseEntity<Object> createAClosingDate(Long idMutualInvesment, ClosingDate closingDate);

    ResponseEntity<Object> closeMutualInvestment(Long idMutualInvesment);

    Long countAll();
}
