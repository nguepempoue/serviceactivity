package com.b2i.activitiesorganisation.service.Subscription;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.dto.request.Subscription.SubscriptionRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.MutualInvestment;
import com.b2i.activitiesorganisation.model.Subscription;
import com.b2i.activitiesorganisation.model.SubscriptionOffer;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.model.feignEntities.User;
import com.b2i.activitiesorganisation.repository.MutualInvestmentRepository;
import com.b2i.activitiesorganisation.repository.RiskProfileRepository;
import com.b2i.activitiesorganisation.repository.SubscriptionOfferRepository;
import com.b2i.activitiesorganisation.repository.SubscriptionRepository;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImplement implements SubscriptionService{

    private final RiskProfileRepository riskProfileRepository;
    private final SubscriptionOfferRepository subscriptionOfferRepository;
    private final FeignService feignService;
    private final SubscriptionRepository subscriptionRepository;
    private final MutualInvestmentRepository mutualInvestmentRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public SubscriptionServiceImplement(RiskProfileRepository riskProfileRepository, SubscriptionOfferRepository subscriptionOfferRepository, FeignService feignService, SubscriptionRepository subscriptionRepository, MutualInvestmentRepository mutualInvestmentRepository) {
        this.riskProfileRepository = riskProfileRepository;
        this.subscriptionOfferRepository = subscriptionOfferRepository;
        this.feignService = feignService;
        this.subscriptionRepository = subscriptionRepository;
        this.mutualInvestmentRepository = mutualInvestmentRepository;
    }

    @Override
    public ResponseEntity<Object> subscribe(String token, SubscriptionRequest subscriptionRequest, Long idSubscriber, Long idSubscriptionOffer) {
        String bearerToken = "Bearer " + token;

/*        //GET RIST PROFIL BY ID
        Optional<RiskProfile> riskProfile = riskProfileRepository.findById(idriskProfile);*/

        //GET SUBSCRIPTION OFFER BY ID
        Optional<SubscriptionOffer> subscriptionOffer = subscriptionOfferRepository.findById(idSubscriptionOffer);

        if(!subscriptionOffer.isPresent()){
            return ResponseHandler.generateError(new Exception(" subscriptionOffer does not exist !"));
        }

        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findMutualInvestmentByIdSubscriptionOffer(subscriptionOffer.get().getId());

        if(!mutualInvestment.isPresent()){
            return ResponseHandler.generateError(new Exception(" mutual investment does not exist !"));
        }
        try {
            User subscriber = getUser(bearerToken, idSubscriber);

            if(subscriber == null) {
                throw new Exception("This subscriber doesn't exist !");
            }
            Utils.checkLongValues(subscriptionRequest.getAmount(), "Amount of subscription");

            // GET USER ACCOUNT
            Account account = findAccountByUserAndAccountType(bearerToken, subscriber.getId(), 3L);


            // IF ACCOUNT NOT FOUND
            if(account == null) {
                throw new Exception("This user doesn't have investissement account !");
            }

            Subscription subscription = new Subscription();

            /* if (!riskProfile.isPresent()){
                return ResponseHandler.generateError(new Exception(" Risk does not exist !"));
            }

            subscription.setRiskProfile(riskProfile.get());
            */

            subscription.setSubscriber(subscriber);
            subscription.setAmount(subscriptionRequest.getAmount());
            subscription.setStatus(PaymentEnum.UNPAID);
            SubscriptionOffer s =  subscriptionOffer.get();
            long amountRentability = subscriptionRequest.getAmount() * mutualInvestment.get().getProfitabilityRate() / 100;
            subscription.setAmountOfProfitability(amountRentability * subscriptionOffer.get().getProfitabilityRate() / 100);
            subscription.setTotalAmountReceivable((amountRentability * subscriptionOffer.get().getProfitabilityRate() / 100) + subscriptionRequest.getAmount());
            Subscription savedSubscription = subscriptionRepository.save(subscription);
            s.getSubscriptions().add(savedSubscription);
            subscriptionOfferRepository.save(s);
            // RETURN
            return ResponseHandler.generateOkResponse("Subscription saved successfully !",
                    savedSubscription);
        }catch (Exception e){
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> update(SubscriptionRequest subscriptionRequest, Long idSubscrition) {
        // GET SUBSCRIPTION
        Optional<Subscription> subscription = subscriptionRepository.findById(idSubscrition);

        try {

            return subscription.map((p) -> {

                // UPDATE REASON
                if (subscriptionRequest.getAmount() != null && !subscriptionRequest.getAmount().equals("")) {
                    p.setAmount(subscriptionRequest.getAmount());
                }

                return ResponseHandler.generateOkResponse("Subscription " + idSubscrition + " has been updated properly !",
                        subscriptionRepository.save(p));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Subscription not found !"));

        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteSubscription(Long idSubscrition) {
        // GET SUBSCRIPTION
        Optional<Subscription> subscription = subscriptionRepository.findById(idSubscrition);
        SubscriptionOffer subscriptionOffer = subscriptionOfferRepository.findSubscriptionOfferByIdSubscription(idSubscrition);
        if(!subscription.isPresent()){
            return ResponseHandler.generateNotFoundResponse("Subscription profile not found !");
        }
        try {
            subscriptionOffer.getSubscriptions().remove(subscription.get());
            return subscription.map((s) -> {
                subscriptionRepository.deleteById(idSubscrition);
                return ResponseHandler.generateOkResponse("Subscription " + idSubscrition + " has properly been deleted !", null);
            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Subscription not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> findSubscriptionById(Long idSubscrition) {
        // GET SUBSCRIPTION BY ID
        Optional<Subscription> subscription = subscriptionRepository.findById(idSubscrition);

        try {
            return subscription.map(s -> ResponseHandler.generateOkResponse("Subscription " + idSubscrition, s))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Subscription not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> findAllSubscriptions() {
        // GET ALL
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        try {

            if(subscriptions.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Offer list", subscriptions);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    //RELEASE OF SUBSCRIPTION
    @Override
    public ResponseEntity<Object> releaseSubscription(String token, Long idSouscription){
        String bearerToken = "Bearer " + token;
        //GET A SUBSCRIPTION
        Optional<Subscription> subscription = subscriptionRepository.findById(idSouscription);

        try{
            // IF SUBSCRIPTION NOT FOUND
            if(!subscription.isPresent()) {
                throw new Exception("This subscription doesn't not exists !");
            }

            if(!(subscription.get().getStatus() == PaymentEnum.PAID)){
                throw new Exception("This subscription is not completed or alredy released !");
            }

            SubscriptionOffer subscriptionOffer = subscriptionOfferRepository.findSubscriptionOfferByIdSubscription(subscription.get().getId());

            if(subscriptionOffer == null) {
                throw new Exception("This subscriptionOffer doesn't not exists !");
            }

            Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findMutualInvestmentByIdSubscriptionOffer(subscriptionOffer.getId());

            if(!mutualInvestment.isPresent()) {
                throw new Exception("This mutualInvestment doesn't not exists !");
            }

            // GET USER ACCOUNT
            Account account = findAccountByUserAndAccountType(bearerToken, subscription.get().getSubscriber().getId(), 3L);

            // GET MUTAUL ACCOUNT
            //Account mutualAccount = getAccountById(bearerToken, mutualInvestment.get().getMutualInvestmentAccount().getId());

            // IF ACCOUNT NOT FOUND
            if(account == null) {
                throw new Exception("This user doesn't have invesment account !");
            }

            // IF ACCOUNT NOT FOUND
//            if(mutualAccount == null) {
//                throw new Exception("This mutual doesn't have mutual account !");
//            }

            // SET USER ACCOUNT BALANCE
            setBalance(bearerToken, account.getId(), -subscription.get().getAmount());


            // SET MUTAUL ACCOUNT BALANCE
            //setBalance(bearerToken, mutualAccount.getId(), subscription.get().getAmount());

            mutualInvestment.get().getMutualInvestmentAccount().setBalance(mutualInvestment.get().getMutualInvestmentAccount().getBalance() + subscription.get().getAmount());

            mutualInvestment.get().setAmountToBeRefunded(subscription.get().getAmount() +  (subscription.get().getAmount() * mutualInvestment.get().getProfitabilityRate() / 100));

            mutualInvestmentRepository.save(mutualInvestment.get());

            subscription.get().setStatus(PaymentEnum.RELEASED);

            //SAVE SOUSCRIPTION
            Subscription savedSubscription = subscriptionRepository.save(subscription.get());
            return ResponseHandler.generateOkResponse("Subscription saved successfully !",
                    savedSubscription);
        }catch (Exception e){
            return Utils.catchException(e);
        }

    }

    /* **************************************** OTHERS **************************************** */

    // GET USER
    private User getUser(String bearerToken, Long id) {

        //String rUser = ResponseStringifier.stringifier(feignService.getUserById(bearerToken, id).getBody());
        String rUser = this.getStringAccount(feignService.getUserById(bearerToken, id).getBody());
        try {
            return mapper.readValue(rUser, User.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    String getStringAccount(Object responseEntity){
        return ResponseStringifier.stringifier(responseEntity);
    }
    // GET ACCOUNT BY USER AND ACCOUNT TYPE
    private Account findAccountByUserAndAccountType(String bearerToken, Long idUser, Long idAccountType) {

        String rAccount = ResponseStringifier.stringifier(getAObjetct(feignService.findAccountByUserAndAccountType(bearerToken, idUser, idAccountType).getBody()));

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

        String rAccount = ResponseStringifier.stringifier(getAObjetct(feignService.setBalance(bearerToken, idAccount, amount).getBody()));

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

    private Account getAccountById(String bearerToken, Long idAccount) {

        String rAccount = ResponseStringifier.stringifier(feignService.getAccountById(bearerToken, idAccount).getBody());

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

    public Object getAObjetct(Object object){
        return  object;
    }
}
