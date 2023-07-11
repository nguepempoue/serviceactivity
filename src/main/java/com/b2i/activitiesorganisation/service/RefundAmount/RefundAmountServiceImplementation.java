package com.b2i.activitiesorganisation.service.RefundAmount;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.dto.request.RefundAmount.RefundAmountRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.repository.MutualInvestmentRepository;
import com.b2i.activitiesorganisation.repository.PaymentMethodRepository;
import com.b2i.activitiesorganisation.repository.RefundAmountRepository;
import com.b2i.activitiesorganisation.repository.SecurityDepositRepository;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefundAmountServiceImplementation implements RefundAmountService{

    private final SecurityDepositRepository securityDepositRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final MutualInvestmentRepository mutualInvestmentRepository;

    private final RefundAmountRepository refundAmountRepository;

    private final FeignService feignService;

    private final ObjectMapper mapper = new ObjectMapper();

    public RefundAmountServiceImplementation(SecurityDepositRepository securityDepositRepository, PaymentMethodRepository paymentMethodRepository, MutualInvestmentRepository mutualInvestmentRepository, RefundAmountRepository refundAmountRepository, FeignService feignService) {

        this.securityDepositRepository = securityDepositRepository;

        this.paymentMethodRepository = paymentMethodRepository;

        this.mutualInvestmentRepository = mutualInvestmentRepository;

        this.refundAmountRepository = refundAmountRepository;
        this.feignService = feignService;
    }

    @Override
    public ResponseEntity<Object> refundAmountSecutityDeposit(String token, Long idSecurityDeposit, Long idPaymentMethod, RefundAmountRequest refundAmountRequest){

        String bearerToken = "Bearer " + token;

        //FIND REFUN
        Optional<SecurityDeposit> securityDeposit = securityDepositRepository.findById(idSecurityDeposit);

        // FIND MUTUAL INVESMENT
        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findMutualInvestmentByIdSecurityDeposit(idSecurityDeposit);

        // FIND PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        RefundAmount refundAmount = new RefundAmount();

        if (!securityDeposit.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Refund not found !");
        }

        if (!mutualInvestment.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Mutual investment not found !");
        }
        if (!paymentMethod.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Payment methode not found !");
        }
        try {
            // CHECK DATE
            if (refundAmountRequest.getDate() == null) {
                throw new Exception("Payment date can't be null !");
            }

            // CHECK AMOUNT PAID
            if (refundAmountRequest.getPaid() == null) {
                throw new Exception("Amount paid can't be null !");
            }

            // CHECK REASON
            if (refundAmountRequest.getProof() == null || refundAmountRequest.getProof().equals("")) {
                throw new Exception("Proof can't be nor null neither an empty string");
            }

            refundAmount.setProof(refundAmountRequest.getProof());

            refundAmount.setPaid(refundAmountRequest.getPaid());

            refundAmount.setDate(refundAmountRequest.getDate());

            refundAmount.setPaymentMethod(paymentMethod.get());

            RefundAmount savedRefundAmount = refundAmountRepository.save(refundAmount);

            long amountToBePayForADate = 0L;

            securityDeposit.get().getRefundAmounts().add(savedRefundAmount);

            securityDeposit.get().setAmountPaid(securityDeposit.get().getAmountPaid() + refundAmountRequest.getPaid());

            if((securityDeposit.get().getAmountToPay() - refundAmountRequest.getPaid() > 0)){
                amountToBePayForADate = securityDeposit.get().getAmountToPay() - refundAmountRequest.getPaid();
            }

            securityDeposit.get().setAmountToPay(amountToBePayForADate);

            SecurityDeposit savedSecurityDeposit =  securityDepositRepository.save(securityDeposit.get());

            if(savedSecurityDeposit.getAmountToPay() == 0L){

                savedSecurityDeposit.setRefundStatus(PaymentEnum.PAID);

            }

            securityDepositRepository.save(savedSecurityDeposit);

            long amountToBeRefunded = 0L;

            if((mutualInvestment.get().getAmountToBeRefunded() - refundAmountRequest.getPaid() > 0)){
                amountToBeRefunded = mutualInvestment.get().getAmountToBeRefunded() - refundAmountRequest.getPaid();
            }

            mutualInvestment.get().setAmountToBeRefunded(amountToBeRefunded);

            mutualInvestment.get().setAmountRefunded(mutualInvestment.get().getAmountRefunded() + refundAmountRequest.getPaid());

            MutualInvestment mutualInvestmentSaved =  mutualInvestmentRepository.save(mutualInvestment.get());

            if(mutualInvestmentSaved.getAmountToBeRefunded() == 0L){

                mutualInvestmentSaved.setRefundStatus(PaymentEnum.PAID);

            }
            // SET MUTUEL ACCOUNT BALANCE
            setBalance(bearerToken,  mutualInvestment.get().getMutualInvestmentAccount().getId(), refundAmountRequest.getPaid());

            return ResponseHandler.generateOkResponse("Amount refundOf successfully !",
                    mutualInvestmentRepository.save(mutualInvestmentSaved));
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public ResponseEntity<Object> findRefundAmountById(Long idSecurityDeposit) {
        // GET RECEIVING PARTY
        Optional<SecurityDeposit> securityDeposit = securityDepositRepository.findById(idSecurityDeposit);

        try {

            return securityDeposit.map(sd -> ResponseHandler.generateOkResponse("SecurityDeposit " + idSecurityDeposit, sd))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("SecurityDeposit not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


  //  ****************************************** OTHERS ********************************************

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
