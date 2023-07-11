package com.b2i.activitiesorganisation.service.SubscriptionPayment;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.dto.request.SubscriptionPayment.SubscriptionPaymentRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.repository.*;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SubscriptionPaymentServiceImplementation implements  SubscriptionPaymentService{


    private final PaymentMethodRepository paymentMethodRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionPaymentRepository subscriptionPaymentRepository;

    private final SubscriptionOfferRepository subscriptionOfferRepository;

    private final MutualInvestmentRepository mutualInvestmentRepository;

    private final FeignService feignService;

    public SubscriptionPaymentServiceImplementation(PaymentMethodRepository paymentMethodRepository, SubscriptionRepository subscriptionRepository, SubscriptionPaymentRepository subscriptionPaymentRepository, SubscriptionOfferRepository subscriptionOfferRepository, MutualInvestmentRepository mutualInvestmentRepository, FeignService feignService) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionPaymentRepository = subscriptionPaymentRepository;
        this.subscriptionOfferRepository = subscriptionOfferRepository;
        this.mutualInvestmentRepository = mutualInvestmentRepository;
        this.feignService = feignService;
    }

    @Override
    public ResponseEntity<Object> createPayment(String token, Long idSubscription, Long idPaymentMethod, SubscriptionPaymentRequest subscriptionPaymentRequest) {
        String bearerToken = "Bearer " + token;
        // GET PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        //GET SUBSCRIPTION
        Optional<Subscription> subscription = subscriptionRepository.findById(idSubscription);


        SubscriptionOffer subscriptionOffer = subscriptionOfferRepository.findSubscriptionOfferByIdSubscription(idSubscription);

        // NEW PAYMENT
            SubscriptionPayment payment = new SubscriptionPayment();

        try {

            // CHECK PAYMENT METHOD
            if (!paymentMethod.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Payment method not found !");
            }

            if (subscriptionOffer == null) {
                return ResponseHandler.generateNotFoundResponse("SubscriptionOffer not found !");
            }

            //CHECK SUBSCRIPTION
            if(!subscription.isPresent()){
                return ResponseHandler.generateNotFoundResponse("subcription not found !");
            }

            // CHECK DATE
            if (subscriptionPaymentRequest.getDate() == null) {
                throw new Exception("Payment date can't be null !");
            }

            // CHECK AMOUNT PAID
            if (subscriptionPaymentRequest.getPaid() == null) {
                throw new Exception("Amount paid can't be null !");
            }

            // CHECK REASON
            if (subscriptionPaymentRequest.getProof() == null || subscriptionPaymentRequest.getProof().equals("")) {
                throw new Exception("Proof can't be nor null neither an empty string");
            }

            //GET MUTUAL INVESMENT
            Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findMutualInvestmentByIdSubscriptionOffer(subscriptionOffer.getId());

            System.out.println("mutualInvestment:: " + mutualInvestment);
            // GET USER ACCOUNT
            Account account = findAccountByUserAndAccountType(bearerToken, subscription.get().getSubscriber().getId(), 3L);

            // IF ACCOUNT NOT FOUND
            if(account == null) {
                throw new Exception("This user doesn't have invesment account !");
            }

            // IF MUTUAL INVESMENT NOT FOUND
            if(!mutualInvestment.isPresent()) {
                throw new Exception("This mutual invesment not found !");
            }

            // SET USER ACCOUNT BALANCE
            setBalance(bearerToken, account.getId(), subscriptionPaymentRequest.getPaid());


            // SETTING VALUES IF ALL TESTS ARE GOOD
            payment.setDate(subscriptionPaymentRequest.getDate());
            payment.setPaid(subscriptionPaymentRequest.getPaid());
            payment.setProof(subscriptionPaymentRequest.getProof());


            // PAYMENT METHOD
            payment.setPaymentMethod(paymentMethod.get());

            // SAVING PAYMENT
            payment = subscriptionPaymentRepository.save(payment);


            // ADDING PAYMENT TO SUBCRIPTION
            Subscription s = subscription.get();
            s.getPayments().add(payment);
            Long oldAmount = 0L;
            if(s.getAmountPaid() != null){
                oldAmount = s.getAmountPaid();
            }
            s.setAmountPaid(oldAmount + subscriptionPaymentRequest.getPaid());
            Subscription saveSubs = subscriptionRepository.save(s);

            AtomicReference<Long> amount = new AtomicReference<>(0L);
            saveSubs.getPayments().forEach(p -> {
                amount.set(amount.get() + p.getPaid());
            });
            if(amount.get() > 0 && amount.get() < saveSubs.getAmount()){
                saveSubs.setStatus(PaymentEnum.NOTCOMPLETE);
            }else if(amount.get() >= saveSubs.getAmount()){
                saveSubs.setStatus(PaymentEnum.PAID);
            }

            subscriptionRepository.save(saveSubs);

            mutualInvestment.get().setCollectedAmount(mutualInvestment.get().getCollectedAmount() + subscriptionPaymentRequest.getPaid());

            mutualInvestmentRepository.save(mutualInvestment.get());

            return ResponseHandler.generateCreatedResponse("Payment created !", payment);
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> findAllPayments() {
        // GET ALL
        List<SubscriptionPayment> payments = subscriptionPaymentRepository.findAll();

        try {

            if(payments.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Offer list", payments);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> updatePayment(Long idPayment, SubscriptionPaymentRequest subscriptionPaymentRequest) {
        // GET PAYMENT
        Optional<SubscriptionPayment> subscriptionPayment = subscriptionPaymentRepository.findById(idPayment);

        try {

            return subscriptionPayment.map((p) -> {

                // UPDATE REASON
                if (subscriptionPaymentRequest.getProof() != null && !subscriptionPaymentRequest.getProof().equals("")) {
                    p.setProof(subscriptionPaymentRequest.getProof());
                }

                return ResponseHandler.generateOkResponse("Payment " + idPayment + " has been updated properly !",
                        subscriptionPaymentRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));

        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> deletePayment(Long idPayment) {
        // GET PAYMENT
        Optional<SubscriptionPayment> payment = subscriptionPaymentRepository.findById(idPayment);

        try {
            return payment.map((p) -> {
                subscriptionPaymentRepository.deleteById(idPayment);
                subscriptionPaymentRepository.save(p);
                return ResponseHandler.generateOkResponse("Payment " + idPayment + " has properly been deleted !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> findPaymentById(Long idPayment) {

        // GET PAYMENT
        Optional<SubscriptionPayment> payment = subscriptionPaymentRepository.findById(idPayment);

        try {
            return payment.map(p -> ResponseHandler.generateOkResponse("Payment " + idPayment, p))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Payment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    /* **************************************** OTHERS **************************************** */

    // GET ACCOUNT BY USER AND ACCOUNT TYPE
    private Account findAccountByUserAndAccountType(String bearerToken, Long idUser, Long idAccountType) {

        String rAccount = ResponseStringifier.stringifier(feignService.findAccountByUserAndAccountType(bearerToken, idUser, idAccountType).getBody());

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
