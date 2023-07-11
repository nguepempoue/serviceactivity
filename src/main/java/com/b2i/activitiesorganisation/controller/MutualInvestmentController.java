package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.AllocationKey.AllocationKeyRequest;
import com.b2i.activitiesorganisation.dto.request.AmountCollected.AmountCollectedRequest;
import com.b2i.activitiesorganisation.dto.request.MutualInvestment.DistributionPercentageRequest;
import com.b2i.activitiesorganisation.dto.request.MutualInvestment.MutualInvestmentRequest;
import com.b2i.activitiesorganisation.dto.request.Refund.RefundRequest;
import com.b2i.activitiesorganisation.dto.request.SecurityDeposit.SecurityDepositRequest;
import com.b2i.activitiesorganisation.model.ClosingDate;
import com.b2i.activitiesorganisation.model.FirstRefundDate;
import com.b2i.activitiesorganisation.service.MutualInvestment.MutualInvestmentServiceImplementation;
import com.b2i.activitiesorganisation.service.SecurityDeposit.SecurityDepositServiceImplementation;
import com.b2i.activitiesorganisation.service.SubscriptionOffer.SubscriptionOfferServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutual-investments")
@CrossOrigin("*")
public class MutualInvestmentController {

    @Autowired
    private MutualInvestmentServiceImplementation mutualInvestmentServiceImplementation;

    @Autowired
    private SecurityDepositServiceImplementation securityDepositServiceImplementation;

    @Autowired
    private SubscriptionOfferServiceImplementation subscriptionOfferServiceImplementation;


    // CREATE
    @PostMapping("/drawee-form/{idDraweeForm}/refund-type/{idRefundType}/profitability-type/{idProfitabilityType}/center/{idCenter}")
    public ResponseEntity<Object> createMutualInvestment(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "idMutualist", defaultValue = "0", required = false) String idMutualist,
            @RequestParam(name = "idFrequency", defaultValue = "0", required = false) String idFrequency,
            @RequestBody MutualInvestmentRequest mutualInvestmentRequest,
            @PathVariable("idDraweeForm") Long idDraweeForm,
            @PathVariable("idRefundType") Long idRefundType,
            @PathVariable("idProfitabilityType") Long idProfitabilityType,
            @PathVariable("idCenter") Long idCenter) {

        return mutualInvestmentServiceImplementation.createMutualInvestment(token, mutualInvestmentRequest, idDraweeForm,
                Long.parseLong(idMutualist),idRefundType, Long.parseLong(idFrequency), idProfitabilityType, idCenter);
    }

    // UPDATE MUTUALINVESTMENT
    @PutMapping("/{idInvestment}")
    public ResponseEntity<Object> updateMutualInvestment(@PathVariable("idInvestment") Long idInvestment, @RequestBody MutualInvestmentRequest mutualInvestmentRequest) {
        return mutualInvestmentServiceImplementation.updateMutualInvestment(idInvestment, mutualInvestmentRequest);
    }

    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAllMutualInvestments() {
        return mutualInvestmentServiceImplementation.findAllMutualInvestments();
    }


    // FIND BY ID
    @GetMapping("/{idInvestment}")
    public ResponseEntity<Object> findMutualInvestmentById(@PathVariable("idInvestment") Long idInvestment) {
        return mutualInvestmentServiceImplementation.findMutualInvestmentById(idInvestment);
    }


    // GENERATE REFUND DATES
    @PatchMapping("/{idInvestment}/generate-refund-dates")
    public ResponseEntity<Object> generateRefundDates(@PathVariable("idInvestment") Long idInvestment, @RequestBody FirstRefundDate firstRefundDate) {
        return mutualInvestmentServiceImplementation.generateRefundDates(idInvestment, firstRefundDate);
    }

    // SET REFUND DATES MANUALLY
    @PatchMapping("/{idInvestment}/set-refund-dates-manually")
    public ResponseEntity<Object> setRefundDatesManually(@PathVariable("idInvestment") Long idInvestment, @RequestBody RefundRequest refundRequest) {
        return mutualInvestmentServiceImplementation.setRefundDatesManually(idInvestment, refundRequest);
    }


    // DELETE
    @DeleteMapping("/{idInvestment}")
    public ResponseEntity<Object> deleteMutualInvestment(@RequestParam("token") String token, @PathVariable("idInvestment") Long idInvestment) {
        return mutualInvestmentServiceImplementation.deleteMutualInvestment(token, idInvestment);
    }


    // UPDATE ALLOCATION KEY IN MUTUAL INVESTMENT
    @PatchMapping("/{idInvestment}/update-allocation-key/{idAllocationKey}")
    public ResponseEntity<Object> updateInvestmentAllocationKey(@PathVariable("idInvestment") Long idInvestment, @PathVariable("idAllocationKey") Long idAllocationKey, @RequestBody AllocationKeyRequest allocationKeyRequest) {
        return mutualInvestmentServiceImplementation.updateInvestmentAllocationKey(idInvestment, idAllocationKey, allocationKeyRequest);
    }


    // ADD SECURITY DEPOSIT
    @PostMapping("/{idInvestment}/add-security-deposit/user/{idUser}")
    public ResponseEntity<Object> addSecurityDeposit(@RequestParam String token, @PathVariable("idInvestment") Long idInvestment, @PathVariable("idUser") Long idUser, @RequestBody SecurityDepositRequest securityDepositRequest) {
        return mutualInvestmentServiceImplementation.addSecurityDeposit(token, idInvestment, idUser, securityDepositRequest);
    }

    // MAKE DISTRIBUTION
    @PatchMapping("/{idInvestment}/make-the-distribution")
    public ResponseEntity<Object> makeDistribution(@RequestParam String token, @PathVariable("idInvestment") Long idInvestment) {
        return mutualInvestmentServiceImplementation.makeDistribution(token, idInvestment);
    }


    // DELETE SECURITY DEPOSIT
    @DeleteMapping("/{idInvestment}/security-deposit/{idDeposit}")
    public ResponseEntity<Object> deleteSecurityDeposit(@PathVariable("idInvestment") Long idMutualInvestment, @PathVariable("idDeposit") Long idDeposit) {
        return securityDepositServiceImplementation.deleteSecurityDeposit(idMutualInvestment, idDeposit);
    }


    // FIND SECURITY DEPOSIT BY USER AND MUTUAL INVESTMENT
    @GetMapping("/{idInvestment}/security-deposit/user/{idUser}")
    public ResponseEntity<Object> findSecurityDepositByUserAndMutualInvestment(@PathVariable("idUser") Long idUser, @PathVariable("idInvestment") Long idMutualInvestment) {
        return securityDepositServiceImplementation.findSecurityDepositByUserAndMutualInvestment(idUser, idMutualInvestment);
    }

    // CREATE SUBSCRIPTION
    @PostMapping("/{idInvestment}/subscription-offers/new/risk-profile/{idProfile}/profitability-type/{idProfitabilityType}/")
    public ResponseEntity<Object> createSubscriptionOffer(@RequestParam String token, @PathVariable("idInvestment") Long idInvestment, @PathVariable("idProfile") Long idRiskProfile, @PathVariable("idProfitabilityType") Long idProfitabilityType, @RequestParam(required = false) Long profitabilityRate) {
        return subscriptionOfferServiceImplementation.createSubscriptionOffer(token, idInvestment, idRiskProfile, idProfitabilityType, profitabilityRate);
    }

    // RELEASE OPERATION
    @PatchMapping("/release/{idInvestment}")
    public ResponseEntity<Object> releaseOperation(@PathVariable("idInvestment") Long idInvestment, @RequestParam String token) {
        return mutualInvestmentServiceImplementation.releaseOperation(token, idInvestment);
    }

    // RELEASE OPERATION
    @PatchMapping("/refund/{idRefund}/idPaymentMethod/{idPaymentMethod}")
    public ResponseEntity<Object> refundOfAmountsCollected(@PathVariable("idRefund") Long idRefund, @PathVariable("idPaymentMethod") Long idPaymentMethod, @RequestParam String token, @RequestBody AmountCollectedRequest amountCollectedRequest) {
        return mutualInvestmentServiceImplementation.refundOfAmountsCollected(token, idRefund, idPaymentMethod,  amountCollectedRequest);
    }

    // GET DISTRIBUTION PERCENTAGE
    @PatchMapping("/get-distribution-percentage/{idMutualInvesment}")
    public ResponseEntity<Object> getDistributionPercentage(@PathVariable("idMutualInvesment") Long idMutualInvesment, @RequestBody DistributionPercentageRequest distributionPercentageRequest) {
        return mutualInvestmentServiceImplementation.getDistributionPercentage(idMutualInvesment,  distributionPercentageRequest);
    }

    @GetMapping("search")
    public ResponseEntity<Object> findMutualInvestmentByName(@RequestParam String name) {
        return mutualInvestmentServiceImplementation.findMutualInvestmentByName(name);
    }

    // CREATE A CLOSING DATE
    @PatchMapping("/create-closing-date/{idMutualInvesment}")
    public ResponseEntity<Object> createAClosingDate(@PathVariable("idMutualInvesment") Long idMutualInvesment, @RequestBody ClosingDate closingDate) {
        return mutualInvestmentServiceImplementation.createAClosingDate(idMutualInvesment,  closingDate);
    }

    // CLOSE MUTUAL INVESTMENT BY ID
    @PatchMapping("/{idInvestment}")
    public ResponseEntity<Object> closeMutualInvestment(@PathVariable("idInvestment") Long idInvestment) {
        return mutualInvestmentServiceImplementation.closeMutualInvestment(idInvestment);
    }
}
