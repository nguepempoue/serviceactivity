package com.b2i.activitiesorganisation.service.MutualInvestment;

import com.b2i.activitiesorganisation.Utils.ResponseStringifier;
import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.constant.DistributionEnum;
import com.b2i.activitiesorganisation.constant.MutualInvesmentEnum;
import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.dto.request.AmountCollected.AmountCollectedRequest;
import com.b2i.activitiesorganisation.dto.request.AllocationKey.AllocationKeyRequest;
import com.b2i.activitiesorganisation.dto.request.MutualInvestment.DistributionPercentageRequest;
import com.b2i.activitiesorganisation.dto.request.MutualInvestment.MutualInvestmentRequest;
import com.b2i.activitiesorganisation.dto.request.Refund.RefundRequest;
import com.b2i.activitiesorganisation.dto.request.SecurityDeposit.SecurityDepositRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.*;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.model.feignEntities.Center;
import com.b2i.activitiesorganisation.model.feignEntities.MutOrganism;
import com.b2i.activitiesorganisation.model.feignEntities.User;
import com.b2i.activitiesorganisation.repository.*;
import com.b2i.activitiesorganisation.service.AllocationKey.AllocationKeyServiceImplementation;
import com.b2i.activitiesorganisation.service.FeignServices.FeignService;
import com.b2i.activitiesorganisation.service.PassiveIncomeFundAccount.PassiveIncomeFundAccountServiceImplementation;
import com.b2i.activitiesorganisation.service.SecurityDeposit.SecurityDepositServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MutualInvestmentServiceImplementation implements MutualInvestmentService {

    @Autowired
    private MutualInvestmentRepository mutualInvestmentRepository;

    @Autowired
    private DraweeFormRepository draweeFormRepository;

    @Autowired
    private RefundTypeRepository refundTypeRepository;

    @Autowired
    private FrequencyRepository frequencyRepository;

    @Autowired
    private ProfitabilityTypeRepository profitabilityTypeRepository;

    @Autowired
    private AllocationKeyServiceImplementation allocationKeyServiceImplementation;

    @Autowired
    private AllocationKeyRepository allocationKeyRepository;

    @Autowired
    private SecurityDepositServiceImplementation securityDepositServiceImplementation;

    @Autowired
    private FeignService feignService;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private AmountCollectedRepository amountCollectedRepository;

    @Autowired
    private PassiveIncomeFundAccountRepository passiveIncomeFundAccountRepository;

    @Autowired
    private PassiveIncomeFundAccountServiceImplementation passiveIncomeFundAccountServiceImplementation;

    @Autowired
    private RefundRepository refundRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    // CREATE
    @Override
    public ResponseEntity<Object> createMutualInvestment(String token, MutualInvestmentRequest mutualInvestmentRequest, Long idDraweeForm, Long idMutualist, Long idRefundType, Long idFrequency, Long idProfitabilityType, Long idCenter) {

        String bearerToken = "Bearer " + token;

        // GET DRAWEE FORM
        Optional<DraweeForm> draweeForm = draweeFormRepository.findById(idDraweeForm);

        // GET REFUND TYPE
        Optional<RefundType> refundType = refundTypeRepository.findById(idRefundType);

        // GET PROFITABILITY TYPE
        Optional<ProfitabilityType> profitabilityType = profitabilityTypeRepository.findById(idProfitabilityType);

        try {

            // DRAWEE FORM
            if (!draweeForm.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Drawee form not found !");
            }

            // REFUND TYPE
            if (!refundType.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Refund userType not found !");
            }

            // PROFITABILITY TYPE
            if (!profitabilityType.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Profitability userType not found !");
            }

            // CHECK STRING VALUES
            Utils.checkStringValues(mutualInvestmentRequest.getName(), "Mutual investment name"); // NAME


            Utils.checkLongValues(mutualInvestmentRequest.getMinimumAmount(), "Mutual investment minimum amount"); // MINIMUM AMOUNT
           // Utils.checkLongValues(mutualInvestmentRequest.getEcheanceDurationInMonths(), "Echeance duration in months"); // ECHEANCE DURATION IN MONTHS

            // NEW MUTUAL INVESTMENT
            MutualInvestment investment = new MutualInvestment(mutualInvestmentRequest.getName(), mutualInvestmentRequest.getMinimumAmount());

            investment.setEcheanceDurationInMonths(mutualInvestmentRequest.getEcheanceDurationInMonths());
            // DATES
            // START DATE
            if (mutualInvestmentRequest.getStartDate() != null) {

                investment.setStartDate(mutualInvestmentRequest.getStartDate());
            }

            // END DATE
            if (mutualInvestmentRequest.getEndDate() != null) {

                investment.setEndDate(mutualInvestmentRequest.getEndDate());
            }

            // SETTING VALUES
            investment.setDraweeForm(draweeForm.get());

            investment.setRefundType(refundType.get());

            investment.setProfitabilityType(profitabilityType.get());

            //GET DIFFERENTS PERCENTAGES

//            investment.setPercentageMutual(mutualInvestmentRequest.getPercentageMutual());
//
//            investment.setPercentageOfFunders(mutualInvestmentRequest.getPercentageOfFunders());
//
//            investment.setPercentageOfGuarantees(mutualInvestmentRequest.getPercentageOfGuarantees());
//
//            investment.setPercentageOfPassiveIncomeFund(mutualInvestmentRequest.getPercentageOfPassiveIncomeFund());

            //GET CENTER
            Center center = getCenter(bearerToken, idCenter);
            // IF CENTER NOT FOUND OR NULL
            if(center == null){
                throw new Exception("Center not found ! May be it has been deleted.");
            }
            investment.setMutualCenter(center);
            // IF DRAWEE FORM IS "PERSONNE PHYSIQUE"
            if (draweeForm.get().getLabel().equals("MUTUALIST")) {

                User mutualist = getUser(bearerToken, idMutualist);
                // IF MUTUALIST NOT FOUND OR NULL
                 if (mutualist == null) {
                        throw new Exception("User not found ! May be it has been deleted.");
                }

                // ELSE
                investment.setUser(mutualist);
            } else if (draweeForm.get().getLabel().equals("PERSONNE PHYSIQUE")) {

                if (mutualInvestmentRequest.getPhysicalPerson() == null) {
                    throw new Exception("Physical person must not be null.");
                }

                User physicalPerson = new User();

                physicalPerson.setFirstName(mutualInvestmentRequest.getPhysicalPerson().getFirstName());

                physicalPerson.setLastName(mutualInvestmentRequest.getPhysicalPerson().getLastName());

                physicalPerson.setPhoneNumber(mutualInvestmentRequest.getPhysicalPerson().getPhoneNumber());

                physicalPerson.setEmail(mutualInvestmentRequest.getPhysicalPerson().getEmail());

                physicalPerson.setUserName(mutualInvestmentRequest.getPhysicalPerson().getUserName());

                physicalPerson.setCity(mutualInvestmentRequest.getPhysicalPerson().getCity());

                investment.setUser(physicalPerson);

            }else {
                if (mutualInvestmentRequest.getMutOrganism() == null) {
                    throw new Exception("Organism must not be null.");
                }

                MutOrganism mutOrganism = new MutOrganism();

                mutOrganism.setOrganismName(mutualInvestmentRequest.getMutOrganism().getOrganismName());

                mutOrganism.setFirstNameRepre(mutualInvestmentRequest.getMutOrganism().getFirstName());

                mutOrganism.setLastNameRepre(mutualInvestmentRequest.getMutOrganism().getLastName());

                mutOrganism.setPhoneNumberRepre(mutualInvestmentRequest.getMutOrganism().getPhoneNumber());

                mutOrganism.setCityRepre(mutualInvestmentRequest.getMutOrganism().getCity());

                mutOrganism.setEmailRepre(mutualInvestmentRequest.getMutOrganism().getEmail());

                investment.setOrganism(mutOrganism);

            }
            // IF REFUND TYPE IS "PERIODIQUEMENT"
            // GET REFUND FREQUENCY

            if (refundType.get().getType().equals("PÉRIODIQUEMENT")) {

                Utils.checkLongValues(idFrequency, "La fréquence de remboursement");

                Utils.checkLongValues(mutualInvestmentRequest.getEcheanceDurationInMonths(), "La urée de l'échéance en mois");

                Optional<Frequency> refundFrequency = frequencyRepository.findById(idFrequency);

                // IF FREQUENCY FOUND OR NOT
                if (!refundFrequency.isPresent()) {
                    return ResponseHandler.generateNotFoundResponse("Refund frequency not found !");
                }

                // SETTING VALUE
                else {
                    investment.setRefundFrequency(refundFrequency.get());
                }
            }


            // IF PROFITABILITY TYPE IS "CERTAIN"
            if (profitabilityType.get().getLabel().equals("CERTAIN")) {

                // CHECK PROFITABILITY RATE
                Utils.checkLongValues(mutualInvestmentRequest.getProfitabilityRate(), "Profitability rate");

                // SETTING
                investment.setProfitabilityRate(mutualInvestmentRequest.getProfitabilityRate()); // PROFITABILITY RATE VALUE

                investment.setProfitabilityAmount(((investment.getProfitabilityRate() * investment.getMinimumAmount()) / 100)); // PROFITABILITY AMOUNT VALUE
            }


            // DEFAULT ALLOCATION KEYS
           //addToAllocationKeyList(investment);

            // CREATE ACCOUNT OF MUTUAL INVESTMENT
            Account account = createAccount(bearerToken, 6L);

            if(passiveIncomeFundAccountServiceImplementation.countAll() == 0L){
                PassiveIncomeFundAccount passiveIncomeFundAccount = new PassiveIncomeFundAccount();
                Account passiveIncome = createAccount(bearerToken, 5L);
                passiveIncomeFundAccount.setAccount(passiveIncome);
                passiveIncomeFundAccountServiceImplementation.createPassiveIncomeFundAccount(passiveIncomeFundAccount);
            }

            investment.setMutualInvestmentAccount(account);

            investment.setMutualInvesmentStatus(MutualInvesmentEnum.BLOCKED);

            investment.setRefundStatus(PaymentEnum.UNPAID);

            investment.setDistributionStatus(DistributionEnum.NOTDISTRIBUTED);

            investment.setStatus(MutualInvesmentEnum.OPEN);

            // SAVE
            return ResponseHandler.generateCreatedResponse("Mutual investment created !", mutualInvestmentRepository.save(investment));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // FIND ALL
    @Override
    public ResponseEntity<Object> findAllMutualInvestments() {

        // GET ALL
        List<MutualInvestment> mutualInvestments = mutualInvestmentRepository.findAll();

        try {

            if (mutualInvestments.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Mutual investment list", mutualInvestments);
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // UPDATE
    @Override
    public ResponseEntity<Object> updateMutualInvestment(Long idInvestment, MutualInvestmentRequest mutualInvestmentRequest) {
        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {
            if (!investment.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Mutual investment not found !");
            }

            if (investment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
                return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
            }

            if (investment.get().getDistributionStatus() == DistributionEnum.DISTRIBUTED) {
                return ResponseHandler.generateNotFoundResponse("Vous ne pouvez plus modifier ce placements car la distribution y a déjà été effectué !");
            }

            investment.get().getOffers().forEach(offer ->{
                if (offer.getSubscriptions().size() > 0) {
                    try {
                        throw new Exception("Vous ne pouvez plus modifier ce placements car la distribution y a déjà été effectuée !");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            investment.get().setName(mutualInvestmentRequest.getName());

            investment.get().setMinimumAmount(mutualInvestmentRequest.getMinimumAmount());

            investment.get().setProfitabilityRate(mutualInvestmentRequest.getProfitabilityRate());

            return ResponseHandler.generateOkResponse("Placement mutualisé modifié avec succès !",
                    mutualInvestmentRepository.save(investment.get()));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // DELETE
    @Override
    public ResponseEntity<Object> deleteMutualInvestment(String token, Long idInvestment) {

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {
            if (!investment.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Mutual investment not found !");
            }

            if (investment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
                return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
            }

            Boolean rsp = deleteAccount("Bearer " + token, investment.get()); // DELETE ACCOUNT

            if(!rsp) {
                throw new Exception("An error happened while deleting mutual investment account !");
            }

            mutualInvestmentRepository.deleteById(idInvestment);
            return ResponseHandler.generateOkResponse("Mutual investment properly deleted !",
                    null);
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // FIND BY ID
    @Override
    public ResponseEntity<Object> findMutualInvestmentById(Long idInvestment) {

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {

            return investment.map(i -> ResponseHandler.generateOkResponse("Mutual investment " + idInvestment, i))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // COUNT ALL
    @Override
    public Long countAll() {
        return mutualInvestmentRepository.count();
    }

    // GENERATE REFUND DATES
    @Override
    public ResponseEntity<Object> generateRefundDates(Long idInvestment, FirstRefundDate firstRefundDate) {

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {

            return investment.map(i -> {

                if (i.getStatus() == MutualInvesmentEnum.CLOSE) {
                    return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
                }

                // IF REFUND DATES HAVE ALREADY BEEN GENERATED
                if (!i.getRefunds().isEmpty()) {
                    return ResponseHandler.generateError(new Exception("Generation of refund dates impossible ! " +
                            "It seems like refund dates have already been set !"));
                }

                // ELSE
                try {
                    i = generateDatesByRefundType(i, firstRefundDate.getDate());
                } catch (Exception e) {
                    return Utils.catchException(e);
                }

                return ResponseHandler.generateOkResponse("TEST", i);

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // SET REFUND DATES MANUALLY
    @Override
    public ResponseEntity<Object> setRefundDatesManually(Long idInvestment, RefundRequest refundRequest) {

        // GET INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {

            return investment.map(i -> {

                if (i.getStatus() == MutualInvesmentEnum.CLOSE) {
                    return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
                }

                // REFUND DATES ALREADY GENERATED OR SET
                /*if (!i.getRefunds().isEmpty()) {
                    return ResponseHandler.generateError(new Exception("Generation of refund dates impossible ! " +
                            "It seems like refund dates have already been set !"));
                }*/

                Refund refund = new Refund();
                refund.setRefundDate(refundRequest.getRefundDate());
                refund.setAmountToBeRefunded(refundRequest.getAmountToBeRefunded());
                refund.setRefundStatus(PaymentEnum.UNPAID);
                i.getRefunds().add(refund);

                /*refundRequests.forEach(refundRequest -> {
                    Refund refund = new Refund();
                    refund.setRefundDate(refundRequest.getRefundDate());
                    refund.setAmountToBeRefunded(refundRequest.getAmountToBeRefunded());
                    refund.setRefundStatus(PaymentEnum.UNPAID);
                    i.getRefunds().add(refund);
                });*/

               // i.setRefundDates(refundDates);
                long totalAmountToBerefund = 0L;
                MutualInvestment savedMutualInvestment = mutualInvestmentRepository.save(i);
                for (Refund myRefund : savedMutualInvestment.getRefunds()) {
                    totalAmountToBerefund = totalAmountToBerefund + myRefund.getAmountToBeRefunded();
                }

                if(totalAmountToBerefund >= savedMutualInvestment.getAmountToBeRefunded()){
                    savedMutualInvestment.setRefundDateStatus(PaymentEnum.COMPLETE);
                }else {
                    savedMutualInvestment.setRefundDateStatus(PaymentEnum.NOTCOMPLETE);
                }

                return ResponseHandler.generateOkResponse("Refund dates have properly been set !",
                        mutualInvestmentRepository.save(savedMutualInvestment));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // UPDATE INVESTMENT ALLOCATION KEY
    @Override
    public ResponseEntity<Object> updateInvestmentAllocationKey(Long idInvestment, Long idAllocationKey, AllocationKeyRequest allocationKeyRequest) {

        // GET ALLOCATION KEY
        Optional<AllocationKey> allocationKey = allocationKeyRepository.findById(idAllocationKey);

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {

            if(!allocationKey.isPresent()) {
                return ResponseHandler.generateNotFoundResponse("Allocation key not found !");
            }

            return investment.map(i -> {

                // CALCULATE ALL PERCENTAGE
                Long allPercentages = 0L;

                for(AllocationKey k : i.getAllocationKeys()) {
                    allPercentages += k.getPercentage();
                }

                // CALCULATE ALL PERCENTAGE - ALLOCATION PERCENTAGE TO UPDATE
                allPercentages -= allocationKey.get().getPercentage();

                // CHECK IF NEW ALLOCATION KEY PERCENTAGE + OTHER PERCENTAGES IS <= 100
                if((allPercentages + allocationKeyRequest.getPercentage()) > 100) {
                    return ResponseHandler.generateError(new Exception("Can't update allocation key percentage ! " +
                            "All percentages is greater than 100%."));
                }
                else {

                    Long idReceivingParty = allocationKey.get().getReceivingParty().getId(); // GET RECEIVING PARTY ID
                    i.getAllocationKeys().remove(allocationKey.get()); // REMOVE ALLOCATION KEY FROM INVESTMENT
                    allocationKeyRepository.deleteById(idAllocationKey); // DELETE ALLOCATION KEY
                    i.getAllocationKeys().add(
                            generateAllocationKeys(i.getMinimumAmount(), i.getProfitabilityAmount(),
                                    allocationKeyRequest.getPercentage(), idReceivingParty))
                    ; // GENERATE NEW ALLOCATION KEY FOR INVESTMENT
                }

                // RETURN AND SAVE
                return ResponseHandler.generateOkResponse("Allocation key properly updated !",
                        mutualInvestmentRepository.save(i));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));

        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // ADD SECURITY DEPOSIT
    @Override
    public ResponseEntity<Object> addSecurityDeposit(String token, Long idInvestment, Long idUser, SecurityDepositRequest securityDepositRequest) {
        String bearerToken = "Bearer " + token;
        // GET INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idInvestment);

        try {

            if(!investment.isPresent()){
                return ResponseHandler.generateError(new Exception("this mutual invesment doesn't exists !"));
            }

            if (investment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
                return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
            }

            Center center = this.getCenterByIdUser(bearerToken, idUser);
            if(center != null){
                if(!center.getId().equals(investment.get().getMutualCenter().getId())){
                    return ResponseHandler.generateError(new Exception("the user does not belong to the shared placement center !"));
                }
            }

            return investment.map(i -> {

                // CHECK AMOUNT OF SECURITY DEPOSIT
                if(securityDepositRequest.getAmount() == null || securityDepositRequest.getAmount() == 0L) {
                    return ResponseHandler.generateError(new Exception("Security deposit can't be null or equals 0 !"));
                }

                // CREATE DEPOSIT AND SAVE
                SecurityDeposit securityDeposit = securityDepositServiceImplementation.createSecurityDeposit(token, idUser, securityDepositRequest);

                // IF SECURITY DEPOSIT HAS NOT BEEN CREATED
                if(securityDeposit == null) {
                    return ResponseHandler.generateError(new Exception("An error happened while creating security deposit !"));
                }

                i.getSecurityDeposits().add(securityDeposit); // ADD TO INVESTMENT
                i.setSecurityDepositUsersNumber((long) i.getSecurityDeposits().size()); // UPDATE SECURITY DEPOSIT USERS NUMBER
                i.setCollectedAmount(i.getCollectedAmount() + securityDeposit.getAmount()); // UPDATE COLLECTED AMOUNT
                i.setMutualInvestmentAccount(setBalance("Bearer " + token, i, securityDeposit.getAmount())); // UPDATE ACCOUNT

                // SAVE
                return ResponseHandler.generateOkResponse("Security deposit has properly been added !",
                        mutualInvestmentRepository.save(i));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    // RELEASE OPERATION
    @Override
    public ResponseEntity<Object> releaseOperation(String token, Long idMutualInvesment) {

        String bearerToken = "Bearer " + token;

        // FIND MUTUAL INVESMENT
        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findById(idMutualInvesment);


        try {

            // CHECK ID MUTUAL INVESMENT EXIST
            if (!mutualInvestment.isPresent()) {
                throw new Exception("this mutual invesment doesn't exists !");
            }

            if (mutualInvestment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
                throw new Exception("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
            }

            AtomicReference<Long> totalAmount = new AtomicReference<>(0L);

            AtomicReference<Long> totalAmountToRefund = new AtomicReference<>(0L);

            mutualInvestment.get().getOffers().stream().filter(Objects::nonNull).forEach(subscriptionOffer -> {

                subscriptionOffer.getSubscriptions().stream().filter(Objects::nonNull).forEach(subscription -> {

                    if(subscription.getStatus() == PaymentEnum.RELEASED){
                        try {
                            totalAmount.set(totalAmount.get() + subscription.getAmount());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    totalAmountToRefund.set(totalAmountToRefund.get() + subscription.getAmount());
                });
            });

            if(!totalAmountToRefund.get().equals(totalAmount.get())){
                throw new Exception("Ce placement mutualisé ne peut pas encore être débloqué car toutes les souscriptions n'ont pas été payé ou libéré !");
            }

            long totalAmountCaution = mutualInvestment.get().getSecurityDeposits().stream().mapToLong(SecurityDeposit::getAmount).sum();

            if(mutualInvestment.get().getAmountToBeRefunded() > totalAmountCaution){
                throw new Exception("You cannot release this pooled investment because the current deposit amount is less than the amount collected !");
            }

            if(mutualInvestment.get().getDraweeForm().getLabel().equals("MUTUALIST")){
                // GET USER ACCOUNT
                Account account = findAccountByUserAndAccountType(bearerToken, mutualInvestment.get().getUser().getId(), 6L);

                // IF ACCOUNT NOT FOUND
                if(account == null) {
                    throw new Exception("This user doesn't have invesment account !");
                }

                // SET USER ACCOUNT BALANCE
                setBalance(bearerToken, account.getId(), totalAmount.get());
            }

            // SET MUTUEL ACCOUNT BALANCE
            //setBalance(bearerToken,  mutualInvestment.get().getMutualInvestmentAccount().getId(), -totalAmount.get());

            mutualInvestment.get().setMutualInvesmentStatus(MutualInvesmentEnum.UNLOCKED);

            mutualInvestment.get().setCollectedAmount(totalAmount.get());

            mutualInvestment.get().setAmountToBeRefunded(totalAmount.get() + (totalAmount.get() * mutualInvestment.get().getProfitabilityRate() / 100));

            return ResponseHandler.generateOkResponse("Mutual invesment released successfully !",
                    mutualInvestmentRepository.save(mutualInvestment.get()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseEntity<Object> createAClosingDate(Long idMutualInvesment, ClosingDate closingDate){
        // FIND MUTUAL INVESMENT
        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findById(idMutualInvesment);

        try {

            // CHECK ID MUTUAL INVESMENT EXIST
            if (!mutualInvestment.isPresent()) {
                throw new Exception("This mutual invesment doesn't exists !");
            }

            if (mutualInvestment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
                throw new Exception("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
            }

            AtomicLong totalPaid = new AtomicLong(0L);
            AtomicLong totalUnpaid = new AtomicLong(0L);
            mutualInvestment.get().getRefunds().forEach(myRefund -> {
                    if(myRefund.getRefundStatus() == PaymentEnum.PAID){
                        totalPaid.set(totalPaid.get() + myRefund.getAmountRefunded());
                    }
                totalUnpaid.set(totalUnpaid.get() + myRefund.getAmountToBeRefunded());
            });

            LocalDate actualDate = LocalDate.now();

            if(mutualInvestment.get().getRefundStatus() != PaymentEnum.EXPIRED){
                throw new Exception("You cannot yet create a closing date for this mutual fund because it's no yet expired !");
            }

            if(mutualInvestment.get().getRefundStatus() == PaymentEnum.RELAUNCHED){
                throw new Exception("Désolé vous ne pouvez plus créer de date de clôture pour ce placement mutualisé car il a déjà été relancé !");
            }

            if(!mutualInvestment.get().getRefunds().stream().max(Comparator.comparing(Refund::getId)).isPresent()){
                throw new Exception("The maximum repayment date doesn't exists !");
            }

            if(mutualInvestment.get().getRefunds().stream().max(Comparator.comparing(Refund::getId)).get().getRefundDate().isBefore(actualDate)){
                throw new Exception("You cannot yet create a closing date for this mutual fund because the repayment dates have not yet expired !");
            }

            if(totalUnpaid.get() == 0L){
                throw new Exception("Sorry you can't create a closing date for this mutualized investment because the amount to be repaid has been paid !");
            }else {

                long different = totalPaid.get() - totalUnpaid.get();

                if((different) < 0){
                    different = different * (-1);
                }

                Refund refund = new Refund();
                refund.setRefundDate(closingDate.getDate());
                refund.setAmountToBeRefunded(different);
                refund.setRefundStatus(PaymentEnum.UNPAID);
                mutualInvestment.get().setRefundStatus(PaymentEnum.RELAUNCHED);
                mutualInvestment.get().getRefunds().add(refund);
            }

            return ResponseHandler.generateOkResponse("The closing date has been generated !",
                    mutualInvestmentRepository.save(mutualInvestment.get()));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    //REIMBURSEMENT OF AN AMOUNT
    @Override
    public ResponseEntity<Object> refundOfAmountsCollected(String token, Long idRefund, Long idPaymentMethod, AmountCollectedRequest amountCollectedRequest){

        String bearerToken = "Bearer " + token;

        //FIND REFUN
        Optional<Refund> refund = refundRepository.findById(idRefund);

        // FIND MUTUAL INVESMENT
        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findMutualInvestmentByIdRefund(idRefund);

        // FIND PAYMENT METHOD
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(idPaymentMethod);

        AmountCollected amountCollected = new AmountCollected();

        if (!refund.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Refund not found !");
        }

        if (refund.get().getRefundStatus() == PaymentEnum.EXPIRED) {
            return ResponseHandler.generateNotFoundResponse("Vous ne pouvez plus effectuer ce paiement car il a expiré !");
        }

        if (!mutualInvestment.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Mutual investment not found !");
        }

        if (mutualInvestment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
            return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
        }

        if (!paymentMethod.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Payment methode not found !");
        }
        try {
            // CHECK DATE
            if (amountCollectedRequest.getDate() == null) {
                throw new Exception("Payment date can't be null !");
            }

            // CHECK AMOUNT PAID
            if (amountCollectedRequest.getPaid() == null) {
                throw new Exception("Amount paid can't be null !");
            }

            // CHECK REASON
            if (amountCollectedRequest.getProof() == null || amountCollectedRequest.getProof().equals("")) {
                throw new Exception("Proof can't be nor null neither an empty string");
            }

            amountCollected.setProof(amountCollectedRequest.getProof());

            amountCollected.setPaid(amountCollectedRequest.getPaid());

            amountCollected.setDate(amountCollectedRequest.getDate());

            amountCollected.setPaymentMethod(paymentMethod.get());

            AmountCollected savedAmountCollected = amountCollectedRepository.save(amountCollected);

            long amountToBeRefunedForADate = 0L;

            refund.get().getAmountCollecteds().add(savedAmountCollected);

            refund.get().setAmountRefunded(refund.get().getAmountRefunded() + amountCollectedRequest.getPaid());

            if((refund.get().getAmountToBeRefunded() - amountCollectedRequest.getPaid() > 0)){
                amountToBeRefunedForADate = refund.get().getAmountToBeRefunded() - amountCollectedRequest.getPaid();
            }

            refund.get().setAmountToBeRefunded(amountToBeRefunedForADate);

            Refund savedRefund =  refundRepository.save(refund.get());

            if(savedRefund.getAmountToBeRefunded() == 0L){

                savedRefund.setRefundStatus(PaymentEnum.PAID);

            }

            refundRepository.save(savedRefund);

            long amountToBeRefunded = 0L;

            if((mutualInvestment.get().getAmountToBeRefunded() - amountCollectedRequest.getPaid() > 0)){
                amountToBeRefunded = mutualInvestment.get().getAmountToBeRefunded() - amountCollectedRequest.getPaid();
            }

            mutualInvestment.get().setAmountToBeRefunded(amountToBeRefunded);

            mutualInvestment.get().setAmountRefunded(mutualInvestment.get().getAmountRefunded() + amountCollectedRequest.getPaid());

            MutualInvestment mutualInvestmentSaved =  mutualInvestmentRepository.save(mutualInvestment.get());

            if(mutualInvestmentSaved.getAmountToBeRefunded() == 0L){

                mutualInvestmentSaved.setRefundStatus(PaymentEnum.PAID);

            }
            // SET MUTUEL ACCOUNT BALANCE
            setBalance(bearerToken,  mutualInvestment.get().getMutualInvestmentAccount().getId(), amountCollectedRequest.getPaid());

            return ResponseHandler.generateOkResponse("Amount refundOf successfully !",
                    mutualInvestmentRepository.save(mutualInvestmentSaved));
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public ResponseEntity<Object> makeDistribution(String token, Long idMutualInvesment){

        String bearerToken = "Bearer " + token;

        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findById(idMutualInvesment);

        if (!mutualInvestment.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Mutual investment not found !");
        }

        if (mutualInvestment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
            return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
        }

        if (mutualInvestment.get().getMutualInvesmentStatus() != MutualInvesmentEnum.UNLOCKED) {
            return ResponseHandler.generateNotFoundResponse("The mutual has not been released !");
        }

        if (mutualInvestment.get().getRefundStatus() != PaymentEnum.PAID) {
            return ResponseHandler.generateNotFoundResponse("Not all payments have yet been made !");
        }

        try{
            Long amountRefunded = mutualInvestment.get().getAmountRefunded();

             if(mutualInvestment.get().getAmountToBeRefunded() == 0L){
                //OBTENIR LE REVENU SUR LE MONTANT PAYE
                Long revenues = mutualInvestment.get().getCollectedAmount() * mutualInvestment.get().getProfitabilityRate() / 100;

                //OBTENIR LE capital SUR LE MONTANT PAYE
                Long capital = amountRefunded - revenues;

                Long revenueForMutual = revenues *  mutualInvestment.get().getPercentageMutual() / 100;

                Long passiveRevenueFund = revenues * mutualInvestment.get().getPercentageOfPassiveIncomeFund() / 100;

                Long revenueForCoution = revenues * mutualInvestment.get().getPercentageOfGuarantees() / 100;

              /*  AllocationKey allocationKeyOfFunders = this.allocationKeyRepository.findAllocationKeyByIdidReceivingParty(1L).orElse(null);

                AllocationKey allocationKeyOfPassiveIncomeFunds =  this.allocationKeyRepository.findAllocationKeyByIdidReceivingParty(4L).orElse(null);

                AllocationKey allocationKeyOfMutual = this.allocationKeyRepository.findAllocationKeyByIdidReceivingParty(3L).orElse(null);

                 assert allocationKeyOfMutual != null;
                 allocationKeyOfMutual.setAmountReceived(allocationKeyOfMutual.getReceivingAmount());

                 AllocationKey allocationKeyOfCaution =  this.allocationKeyRepository.findAllocationKeyByIdidReceivingParty(2L).orElse(null);

                 assert allocationKeyOfCaution != null;
                 allocationKeyOfCaution.setAmountReceived(allocationKeyOfCaution.getReceivingAmount());*/

                 AtomicReference<AllocationKey> allocationKeyOfFunders = new AtomicReference<>(new AllocationKey());

                 AtomicReference<AllocationKey> allocationKeyOfPassiveIncomeFunds = new AtomicReference<>(new AllocationKey());

                 mutualInvestment.get().getAllocationKeys().forEach(allocationKey -> {
                     if(allocationKey.getReceivingParty().getId() == 1L){
                          allocationKeyOfFunders.set(allocationKey);
                          allocationKey.setReceivingAmount(mutualInvestment.get().getCollectedAmount() + mutualInvestment.get().getCollectedAmount() * mutualInvestment.get().getProfitabilityRate() / 100 * mutualInvestment.get().getPercentageOfFunders() / 100);
                     }else if (allocationKey.getReceivingParty().getId() == 2L){
                         allocationKey.setAmountReceived(revenueForCoution);
                         allocationKey.setReceivingAmount(revenueForCoution);
                     }else if(allocationKey.getReceivingParty().getId() == 3L){
                         allocationKey.setAmountReceived(revenueForMutual);
                         allocationKey.setReceivingAmount(revenueForMutual);
                     }else if(allocationKey.getReceivingParty().getId() == 4L){
                         allocationKeyOfPassiveIncomeFunds.set(allocationKey);
                         allocationKey.setReceivingAmount(passiveRevenueFund);
                     }

                 });

                this.makeDistributionForFunders(mutualInvestment.get().getOffers(), capital, mutualInvestment.get().getCollectedAmount(), allocationKeyOfFunders.get());

                this.makeRevenuDistributionForFunders(bearerToken ,mutualInvestment.get().getOffers(), mutualInvestment.get().getProfitabilityRate(), mutualInvestment.get().getPercentageOfFunders(), allocationKeyOfFunders.get(), allocationKeyOfPassiveIncomeFunds.get());

                this.makeRevenuDistributionForCaution(mutualInvestment.get().getSecurityDeposits(), revenueForCoution);

                this.makeRevenueDistributionForMutual(bearerToken, mutualInvestment.get(), revenueForMutual);

                this.makeRevenueDistributionForPassiveIncomeFunds(bearerToken, passiveRevenueFund);

                mutualInvestment.get().setDistributionStatus(DistributionEnum.DISTRIBUTED);

            }else if(mutualInvestment.get().getAmountToBeRefunded() > 0L){

                 return ResponseHandler.generateNotFoundResponse("Touts les remoursements n'ont pas été effectués !");

                /* if(mutualInvestment.get().getSecurityDeposits() != null){

                     //Long diffrence = mutualInvestment.get().getAmountToBeRefunded() - amountRefunded;

                     AtomicReference<Long> totalAmounCaution = new AtomicReference<>(0L);

                     mutualInvestment.get().getSecurityDeposits().stream().filter(Objects::nonNull).forEach(securityDeposit -> {
                         totalAmounCaution.set(totalAmounCaution.get() + securityDeposit.getAmount());
                     });

                     Long percentageToPayByCaution = mutualInvestment.get().getAmountToBeRefunded() * 100 / totalAmounCaution.get();

                     AtomicReference<Long> totalAmountToPayByCaution = new AtomicReference<>(0L);

                     if(totalAmounCaution.get() >= mutualInvestment.get().getAmountToBeRefunded()){

                         mutualInvestment.get().getSecurityDeposits().stream().filter(Objects::nonNull).forEach(securityDeposit -> {

                             totalAmountToPayByCaution.set(totalAmountToPayByCaution.get() + (securityDeposit.getAmount() * percentageToPayByCaution / 100));

                             securityDeposit.setAmountPaid(securityDeposit.getAmount() * percentageToPayByCaution / 100);
                         });
                         long revenues = (amountRefunded + totalAmountToPayByCaution.get()) * mutualInvestment.get().getProfitabilityRate() / 100;
                         //OBTENIR LE capital SUR LE MONTANT PAYE
                         Long capital = (amountRefunded + totalAmountToPayByCaution.get()) - revenues;

                         Long revenueForMutual = revenues *  mutualInvestment.get().getPercentageMutual() / 100;

                         Long passiveRevenueFund = revenues * mutualInvestment.get().getPercentageOfPassiveIncomeFund() / 100;

                         Long revenueForCoution = revenues * mutualInvestment.get().getPercentageOfGuarantees() / 100;

                         this.makeDistributionForFunders(mutualInvestment.get().getOffers(), capital, mutualInvestment.get().getCollectedAmount());

                         this.makeRevenuDistributionForFunders(bearerToken, mutualInvestment.get().getOffers(), mutualInvestment.get().getProfitabilityRate(), mutualInvestment.get().getPercentageOfFunders());

                         this.makeRevenuDistributionForCaution(mutualInvestment.get().getSecurityDeposits(), revenueForCoution);

                         this.makeRevenueDistributionForMutual(bearerToken, mutualInvestment.get(), revenueForMutual);

                         this.makeRevenueDistributionForPassiveIncomeFunds(bearerToken, passiveRevenueFund);

                         mutualInvestment.get().setDistributionStatus(DistributionEnum.DISTRIBUTED);

                     }
                     *//*else{
                         mutualInvestment.get().getSecurityDeposits().stream().filter(Objects::nonNull).forEach(securityDeposit -> {

                             totalAmountToPayByCaution.set(totalAmountToPayByCaution.get() + (securityDeposit.getAmount()));

                             securityDeposit.setAmountPaid(securityDeposit.getAmount());
                         });

                         //long revenues = (amountRefunded + totalAmountToPayByCaution.get()) * mutualInvestment.get().getProfitabilityRate() / 100;
                         //OBTENIR LE capital SUR LE MONTANT PAYE
                         //Long capital = (amountRefunded + totalAmountToPayByCaution.get()) - revenues;

                         Long capital = amountRefunded + totalAmountToPayByCaution.get();

                         this.makeDistributionForFunders(mutualInvestment.get().getOffers(), capital, mutualInvestment.get().getCollectedAmount());

                         mutualInvestment.get().setDistributionStatus(DistributionEnum.DISTRIBUTED);

                     }*//*
                 }*/
               /*  else{
                     long revenues = amountRefunded * mutualInvestment.get().getProfitabilityRate() / 100;
                     //OBTENIR LE capital SUR LE MONTANT PAYE
                    // Long capital = amountRefunded - revenues;


                     this.makeDistributionForFunders(mutualInvestment.get().getOffers(), amountRefunded, mutualInvestment.get().getCollectedAmount());

                     mutualInvestment.get().setDistributionStatus(DistributionEnum.DISTRIBUTED);

                 }
*/

             }

            return ResponseHandler.generateCreatedResponse("The distribution has been completed !", mutualInvestmentRepository.save(mutualInvestment.get()));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> getDistributionPercentage(Long idMutualInvesment, DistributionPercentageRequest distributionPercentageRequest){

        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findById(idMutualInvesment);

        if (!mutualInvestment.isPresent()) {
            return ResponseHandler.generateNotFoundResponse("Mutual investment not found !");
        }

        if(!mutualInvestment.get().getOffers().isEmpty()){
            return ResponseHandler.generateNotFoundResponse("You can no longer modify these percentages because an offer has already been created.");
        }

        try{

            if (mutualInvestment.get().getStatus() == MutualInvesmentEnum.CLOSE) {
                throw new Exception("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
            }
            // CHECK LONG VALUES
            Utils.checkLongValues(distributionPercentageRequest.getPercentageMutual(), "Percentage mutual");

            Utils.checkLongValues(distributionPercentageRequest.getPercentageOfFunders(), "Percentage on the provider of funds");

            Utils.checkLongValues(distributionPercentageRequest.getPercentageOfGuarantees(), "Percentage of guarantees");

            Utils.checkLongValues(distributionPercentageRequest.getPercentageOfPassiveIncomeFund(), "Percentage of passive income fund");

            if((distributionPercentageRequest.getPercentageMutual() + distributionPercentageRequest.getPercentageOfFunders() + distributionPercentageRequest.getPercentageOfPassiveIncomeFund() + distributionPercentageRequest.getPercentageOfGuarantees()) != 100){
                throw new Exception("the total percentage must be 100.");
            }

            mutualInvestment.get().setPercentageMutual(distributionPercentageRequest.getPercentageMutual());

            mutualInvestment.get().setPercentageOfFunders(distributionPercentageRequest.getPercentageOfFunders());

            mutualInvestment.get().setPercentageOfGuarantees(distributionPercentageRequest.getPercentageOfGuarantees());

            mutualInvestment.get().setPercentageOfPassiveIncomeFund(distributionPercentageRequest.getPercentageOfPassiveIncomeFund());

            if(!mutualInvestment.get().getAllocationKeys().isEmpty()){

                mutualInvestment.get().getAllocationKeys().clear();

            }

            // POURVOYEUR DE FONDS
            mutualInvestment.get().getAllocationKeys().add(generateAllocationKeys(mutualInvestment.get().getMinimumAmount(), mutualInvestment.get().getProfitabilityAmount(), distributionPercentageRequest.getPercentageOfFunders(), 1L));

            // CAUTIONS
            mutualInvestment.get().getAllocationKeys().add(generateAllocationKeys(mutualInvestment.get().getMinimumAmount(), mutualInvestment.get().getProfitabilityAmount(), distributionPercentageRequest.getPercentageOfGuarantees(), 2L));

            // MUTUELLE
            mutualInvestment.get().getAllocationKeys().add(generateAllocationKeys(mutualInvestment.get().getMinimumAmount(), mutualInvestment.get().getProfitabilityAmount(), distributionPercentageRequest.getPercentageMutual(), 3L));

            // FONDS DE REVENUS PASSIFS
            mutualInvestment.get().getAllocationKeys().add(generateAllocationKeys(mutualInvestment.get().getMinimumAmount(), mutualInvestment.get().getProfitabilityAmount(), distributionPercentageRequest.getPercentageOfPassiveIncomeFund(), 4L));

            return ResponseHandler.generateCreatedResponse("The distribution has been completed !", mutualInvestmentRepository.save(mutualInvestment.get()));
        }catch (Exception e){
            return Utils.catchException(e);
        }
    }

    @Override
    public ResponseEntity<Object> closeMutualInvestment(Long idMutualInvesment){

        //GET MUTUAL INVESTMENT
        Optional<MutualInvestment> investment = mutualInvestmentRepository.findById(idMutualInvesment);

        try {

            if (!investment.isPresent()) {
                return ResponseHandler.generateNoContentResponse("Mutual investment doesn't exists !");
            }

            if(investment.get().getDistributionStatus() != DistributionEnum.DISTRIBUTED){
                return ResponseHandler.generateNoContentResponse("Ce placement mutualisé ne peux pas encore être fermé car la distribution n'y a pas encore été effectuée !");
            }

            investment.get().setStatus(MutualInvesmentEnum.CLOSE);

            return ResponseHandler.generateOkResponse("Mutual investment list", this.mutualInvestmentRepository.save(investment.get()));
        } catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    public void makeDistributionForFunders(List<SubscriptionOffer> offers,  Long capital, Long collectedAmount, AllocationKey allocationKey){
        Long rateOfReturnPerPerson = capital * 100 / collectedAmount;

        AtomicReference<Long> amountReceived = new AtomicReference<>(0L);
        offers.forEach(subscriptionOffer -> {
            subscriptionOffer.getSubscriptions().stream().filter(subscription -> subscription.getStatus() == PaymentEnum.RELEASED).forEach(subsp -> {
                subsp.setTotalAmountReceived(subsp.getTotalAmountReceived() + (subsp.getAmountPaid() * rateOfReturnPerPerson / 100));
                if(allocationKey.getAmountReceived() != null){
                    amountReceived.set(allocationKey.getAmountReceived());
                }
                allocationKey.setAmountReceived(amountReceived.get() + subsp.getTotalAmountReceived());

            });
        });
    }

    public void makeRevenuDistributionForFunders(String bearerToken, List<SubscriptionOffer> offers, Long rateMutualInvestment, Long rateForFunders, AllocationKey allocationKeyOfFonders, AllocationKey allocationKeyOfPassiveIncomeFunds){
        AtomicReference<Long> amountReceiveOfFunders = new AtomicReference<>(0L);
        AtomicReference<Long> amountReceiveOfPassiveIncomeFunds = new AtomicReference<>(0L);
        AtomicReference<Long> adjustedAmountOfFunders = new AtomicReference<>(0L);
        AtomicReference<Long> adjustedAmountOfPassiveIncomeFunds = new AtomicReference<>(0L);
        AtomicReference<Long> totalRevenueForPassiveInvestmentFund = new AtomicReference<>(0L);
        AtomicReference<Long> totalAmountReceived = new AtomicReference<>(0L);
        offers.forEach(subscriptionOffer -> {
            subscriptionOffer.getSubscriptions().stream().filter(subscription -> subscription.getStatus() == PaymentEnum.RELEASED).forEach(subsp -> {
                Long revenuBySubscription = subsp.getAmount() * rateMutualInvestment / 100;
                Long revenueForAllFunders = revenuBySubscription * rateForFunders / 100;
                Long revenueForFunder = revenuBySubscription * subscriptionOffer.getProfitabilityRate() / 100;
                Long revenueForPassiveInvestmentFund = revenueForAllFunders - revenueForFunder;
                totalRevenueForPassiveInvestmentFund.set(totalRevenueForPassiveInvestmentFund.get() + revenueForPassiveInvestmentFund);
                subsp.setTotalAmountReceived(subsp.getTotalAmountReceived() + revenueForFunder);
                totalAmountReceived.set(totalAmountReceived.get() + subsp.getTotalAmountReceived());
                if(allocationKeyOfFonders.getAmountReceived() != null){
                    amountReceiveOfFunders.set(allocationKeyOfFonders.getAmountReceived());
                }
                if(allocationKeyOfFonders.getAdjustedAmount() != null){
                    adjustedAmountOfFunders.set(allocationKeyOfFonders.getAdjustedAmount());
                }
                if(allocationKeyOfPassiveIncomeFunds.getAmountReceived() != null){
                    amountReceiveOfPassiveIncomeFunds.set(allocationKeyOfPassiveIncomeFunds.getAmountReceived());
                }
                if(allocationKeyOfPassiveIncomeFunds.getAmountReceived() != null){
                    adjustedAmountOfPassiveIncomeFunds.set(allocationKeyOfPassiveIncomeFunds.getAmountReceived());
                }
                allocationKeyOfFonders.setAdjustedAmount(adjustedAmountOfFunders.get() - revenueForPassiveInvestmentFund);
                allocationKeyOfPassiveIncomeFunds.setAdjustedAmount(adjustedAmountOfPassiveIncomeFunds.get() + revenueForPassiveInvestmentFund);


                this.makeRevenueDistributionForPassiveIncomeFunds(bearerToken, revenueForPassiveInvestmentFund);
            });
        });
    //    allocationKeyOfFonders.setReceivingAmount(totalAmountReceived.get());
//        allocationKeyOfFonders.setAmountReceived(allocationKeyOfFonders.getReceivingAmount() - totalRevenueForPassiveInvestmentFund.get());
        allocationKeyOfFonders.setAmountReceived(totalAmountReceived.get());
        allocationKeyOfPassiveIncomeFunds.setAmountReceived(allocationKeyOfPassiveIncomeFunds.getReceivingAmount() + totalRevenueForPassiveInvestmentFund.get());
    }

    public void makeRevenuDistributionForCaution(List<SecurityDeposit> securityDeposits,Long revenues){
        if(!securityDeposits.isEmpty()){
            AtomicReference<Long> totalCaution = new AtomicReference<>(0L);
            securityDeposits.forEach(securityDeposit -> {
                totalCaution.set(totalCaution.get() + securityDeposit.getAmount());
            });

            securityDeposits.forEach(securityDeposit -> {
                securityDeposit.setAmountReceived(securityDeposit.getAmountReceived() + (securityDeposit.getAmount() * revenues / totalCaution.get()));
            });
        }
    }

    public void makeRevenueDistributionForMutual(String bearerToken, MutualInvestment mutualInvestment, Long revenues){
        this.setBalance(bearerToken, mutualInvestment, revenues);
    }

    public void makeRevenueDistributionForPassiveIncomeFunds(String bearerToken, Long revenues) {
        Optional<PassiveIncomeFundAccount> passiveIncomeFundAccount = this.passiveIncomeFundAccountRepository.findById(1L);
        if(passiveIncomeFundAccount.isPresent()){
            if(passiveIncomeFundAccount.get().getAccount().getBalance() == null){
                passiveIncomeFundAccount.get().getAccount().setBalance(revenues);
            }else{
                passiveIncomeFundAccount.get().getAccount().setBalance(passiveIncomeFundAccount.get().getAccount().getBalance() + revenues);
            }

            this.setBalance(bearerToken, passiveIncomeFundAccount.get().getAccount().getId(), revenues);
        }

    }

    @Override
    public ResponseEntity<Object> findMutualInvestmentByName(String name) {

        try {
            List<MutualInvestment> mutualInvestments = mutualInvestmentRepository.findMutualInvestmentByName(name);
            if (mutualInvestments.isEmpty()) {
                return ResponseHandler.generateResponse("MutualInvestments list", HttpStatus.NO_CONTENT, null);
            }

            return ResponseHandler.generateResponse("mutualInvestments list", HttpStatus.OK, mutualInvestments);
        } catch (Exception e) {
            return ResponseHandler.generateError(e);
        }
    }

    /* **************************************** OTHERS **************************************** */

    // GET USER
    private User getUser(String bearerToken, Long id) {

        User u = new User();
        String rUser = ResponseStringifier.stringifier(feignService.getUserById(bearerToken, id).getBody());
        try {
            return mapper.readValue(rUser, User.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    //GET CENTER
    private Center getCenter(String bearerToken, Long id){
        String rCenter = ResponseStringifier.stringifier(feignService.getCenterById(bearerToken, id).getBody());
        try {
            return mapper.readValue(rCenter, Center.class);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    //GET CENTER BY ID USER
    private Center getCenterByIdUser(String bearerToken, Long id){
        String rCenter = ResponseStringifier.stringifier(feignService.getCenterByIdUser(bearerToken, id).getBody());
        try{
            return mapper.readValue(rCenter, Center.class);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // GET ACCOUNT AND UPDATE BALANCE
    private Account setBalance(String bearerToken, MutualInvestment investment, Long amount) {

        String rAccount = ResponseStringifier.stringifier(feignService.setBalance(bearerToken, investment.getMutualInvestmentAccount().getId(), amount).getBody());

        try {
            return mapper.readValue(rAccount, Account.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    // CREATE ACCOUNT
    private Account createAccount(String bearerToken, Long idAccountType) {

        Account a = new Account();
        String rAccount = ResponseStringifier.stringifier(feignService.createAccount(bearerToken, idAccountType).getBody());
        if (!rAccount.equals("")) {
            try {
                a = mapper.readValue(rAccount, Account.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            a = null;
        }
        return a;
    }

    // DELETE MUTUAL INVESTMENT ACCOUNT
    private Boolean deleteAccount(String bearerToken, MutualInvestment investment) {
        String rAccount = ResponseStringifier.getStatus(feignService.deleteAccount(
                bearerToken, investment.getMutualInvestmentAccount().getId()).getBody()
        );
        try {
            if (!rAccount.equals("OK")) {
                throw new Exception("Account can't be deleted properly !");
            }

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    // GENERATE DATES
    private MutualInvestment generateDatesByRefundType(MutualInvestment mutualInvestment, LocalDate firstRefundDate) throws Exception {
        // GET REFUND TYPE OF THIS INVESTMENT
        RefundType refundType = mutualInvestment.getRefundType();

        switch (refundType.getType()) {

            // A L'ÉCHÉANCE
            case "A L'ÉCHÉANCE": {
                // ADD END DATE TO REFUND DATES
                //mutualInvestment.getRefundDates().add(mutualInvestment.getEndDate());
                Refund refund = new Refund();
                refund.setRefundDate(mutualInvestment.getEndDate());
                refund.setAmountToBeRefunded(mutualInvestment.getAmountToBeRefunded());
                refund.setRefundStatus(PaymentEnum.UNPAID);
                mutualInvestment.getRefunds().add(refund);
            }
            break;

            // PÉRIODIQUEMENT
            case "PÉRIODIQUEMENT": {
                if (firstRefundDate == null) {
                    throw new Exception("first refund date cannot be null !");
                }
                // ADD ALL DATES
                generateDatesByFrequency(mutualInvestment.getRefundFrequency(),mutualInvestment.getAmountToBeRefunded(), mutualInvestment.getRefunds(),
                        firstRefundDate, mutualInvestment.getEcheanceDurationInMonths());
            }
            break;

            // AVEC DIFFÉRÉ
            case "AVEC DIFFÉRÉ": {

                // IMPOSSIBLE
                throw new Exception("Generation of refund dates impossible with refund userType 'AVEC DIFFÉRÉ' ! You have to set manually all refund dates.");
            }

            // ELSE
            default: {
                throw new Exception("Generation of refund dates impossible ! Unknown refund userType.");
            }
        }

        // SAVE
        return mutualInvestmentRepository.save(mutualInvestment);

        //return null ;
    }

    // GENERATE REFUND DATES ACCORDING TO FREQUENCY
    private void generateDatesByFrequency(Frequency frequency, Long amountToBeRefunded, List<Refund> refunds, LocalDate firstRefundDate, Long durationInMonths) throws Exception {

        // LAST REFUND DATE
        LocalDate lastRefundDate = firstRefundDate.plusMonths(durationInMonths);

        // NUMBER OF WEEKS BETWEEN LAST AND FIRST REFUND DATES
        long weeksBetween = ChronoUnit.WEEKS.between(firstRefundDate, lastRefundDate);

        // NUMBER OF MONTHS BETWEEN LAST AND FIRST REFUND DATES
        long monthsBetween = ChronoUnit.MONTHS.between(firstRefundDate, lastRefundDate);

        int i = 0; // COUNTER

        // GENERATE DATES UNTIL LAST REFUND DATE
        switch (frequency.getLabel()) {

            // HEBDOMADAIRE
            case "HEBDOMADAIRE":{
                Long amountToPayByDate = amountToBeRefunded / weeksBetween;

                long firstAmountToPayByDate = 0L;

                if((amountToPayByDate * weeksBetween) < amountToBeRefunded){
                    firstAmountToPayByDate = amountToBeRefunded - (amountToPayByDate * weeksBetween);
                }

                Refund refund = new Refund();
                refund.setAmountToBeRefunded(amountToPayByDate + firstAmountToPayByDate);
                refund.setRefundDate(firstRefundDate);
                refund.setRefundStatus(PaymentEnum.UNPAID);
                // GET THE FIRST DATE
                refunds.add(refund);

                while (i < weeksBetween - 1) {
                    Refund refundByWeek = new Refund();
                    refundByWeek.setAmountToBeRefunded(amountToPayByDate);
                    refundByWeek.setRefundDate(firstRefundDate.plusWeeks(i + 1));
                    refundByWeek.setRefundStatus(PaymentEnum.UNPAID);
                    refunds.add(refundByWeek); // ADD ONE WEEK
                    i++;
                }
        }
        break;

        // MENSUELLE
        case "MENSUELLE": {
            Long amountToPayByDate = amountToBeRefunded / monthsBetween;

            long firstAmountToPayByDate = 0L;

            if((amountToPayByDate * monthsBetween) < amountToBeRefunded){
                firstAmountToPayByDate = amountToBeRefunded - (amountToPayByDate * monthsBetween);
            }

            Refund refund = new Refund();
            refund.setAmountToBeRefunded(amountToPayByDate + firstAmountToPayByDate);
            refund.setRefundDate(firstRefundDate);
            refund.setRefundStatus(PaymentEnum.UNPAID);
            // GET THE FIRST DATE
            refunds.add(refund);
            while (i < monthsBetween - 1) {
                Refund refundByMonth = new Refund();
                refundByMonth.setAmountToBeRefunded(amountToPayByDate);
                refundByMonth.setRefundDate(firstRefundDate.plusMonths(i + 1));
                refundByMonth.setRefundStatus(PaymentEnum.UNPAID);
                refunds.add(refundByMonth); // ADD ONE MONTH
                i++;
            }
        }
        break;

        // BIMENSUELLE
        case "BIMENSUELLE": {

            Long amountToPayByDate = (amountToBeRefunded / monthsBetween) * 2;

            long firstAmountToPayByDate = 0L;

            if((amountToPayByDate * monthsBetween / 2) < amountToBeRefunded){
                firstAmountToPayByDate = amountToBeRefunded - (amountToPayByDate * monthsBetween / 2);
            }

            Refund refund = new Refund();
            refund.setAmountToBeRefunded(amountToPayByDate + firstAmountToPayByDate);
            refund.setRefundDate(firstRefundDate);
            refund.setRefundStatus(PaymentEnum.UNPAID);

            // GET THE FIRST TWO DATE
            refunds.add(refund);
            while (i < monthsBetween - 2) {
                Refund refundByTwoMonth = new Refund();
                refundByTwoMonth.setAmountToBeRefunded(amountToPayByDate);
                refundByTwoMonth.setRefundDate(firstRefundDate.plusMonths(i + 2));
                refundByTwoMonth.setRefundStatus(PaymentEnum.UNPAID);

                refunds.add(refundByTwoMonth); // ADD TWO MONTHS
                i += 2;
            }
        }
        break;

        // TRIMESTRIELLE
        case "TRIMESTRIELLE": {

            Long amountToPayByDate = (amountToBeRefunded / monthsBetween) * 3;

            long firstAmountToPayByDate = 0L;

            if((amountToPayByDate * monthsBetween / 3) < amountToBeRefunded){
                firstAmountToPayByDate = amountToBeRefunded - (amountToPayByDate * monthsBetween / 3);
            }

            Refund refund = new Refund();
            refund.setAmountToBeRefunded(amountToPayByDate + firstAmountToPayByDate);
            refund.setRefundDate(firstRefundDate);
            refund.setRefundStatus(PaymentEnum.UNPAID);

            // GET THE FIRST THREE DATE
            refunds.add(refund);
            while (i < monthsBetween - 3) {
                Refund refundByThreeMonth = new Refund();
                refundByThreeMonth.setAmountToBeRefunded(amountToPayByDate);
                refundByThreeMonth.setRefundDate(firstRefundDate.plusMonths(i + 3));
                refundByThreeMonth.setRefundStatus(PaymentEnum.UNPAID);

                refunds.add(refundByThreeMonth); // ADD THREE MONTHS
                i += 3;
            }
        }
        break;

        // SEMESTRIELLE
        case "SEMESTRIELLE": {

            Long amountToPayByDate = (amountToBeRefunded / monthsBetween) * 6;

            long firstAmountToPayByDate = 0L;

            if((amountToPayByDate * monthsBetween / 6) < amountToBeRefunded){
                firstAmountToPayByDate = amountToBeRefunded - (amountToPayByDate * monthsBetween / 6);
            }

            Refund refund = new Refund();
            refund.setAmountToBeRefunded(amountToPayByDate + firstAmountToPayByDate);
            refund.setRefundDate(firstRefundDate);
            refund.setRefundStatus(PaymentEnum.UNPAID);

            // GET THE FIRST SIX DATE
            refunds.add(refund);
            while (i < monthsBetween - 6) {
                Refund refundBySixMonth = new Refund();
                refundBySixMonth.setAmountToBeRefunded(amountToPayByDate);
                refundBySixMonth.setRefundDate(firstRefundDate.plusMonths(i + 6));
                refundBySixMonth.setRefundStatus(PaymentEnum.UNPAID);

                refunds.add(refundBySixMonth); // ADD SIX MONTHS
                i += 6;
            }
        }
        break;

        // ANNUELLE
        case "ANNUELLE": {

            long yearsBetween = ChronoUnit.YEARS.between(firstRefundDate, lastRefundDate);

            Long amountToPayByDate = amountToBeRefunded / yearsBetween;

            long firstAmountToPayByDate = 0L;

            if((amountToPayByDate * yearsBetween) < amountToBeRefunded){
                firstAmountToPayByDate = amountToBeRefunded - (amountToPayByDate * yearsBetween);
            }

            Refund refund = new Refund();
            refund.setAmountToBeRefunded(amountToPayByDate + firstAmountToPayByDate);
            refund.setRefundDate(firstRefundDate);
            refund.setRefundStatus(PaymentEnum.UNPAID);
            // GET THE FIRST DATE
            refunds.add(refund);

            while (i < yearsBetween - 1) {
                Refund refundByYear = new Refund();
                refundByYear.setAmountToBeRefunded(amountToPayByDate);
                refundByYear.setRefundDate(firstRefundDate.plusYears(i + 1));
                refundByYear.setRefundStatus(PaymentEnum.UNPAID);

                refunds.add(refundByYear);
                i++;
            }
        }
        break;

        default: {
            throw new Exception("Frequency not found !");
        }
    }

    }

    // GENERATE DEFAULT ALLOCATION KEYS
    private AllocationKey generateAllocationKeys(Long minimumAmount, Long profitabilityAmount, Long percentage, Long idReceivingParty) {

        // CREATE ALLOCATION KEY
        AllocationKey allocationKey = allocationKeyServiceImplementation.createAllocationKey(new AllocationKeyRequest(percentage), idReceivingParty);

        long amountToReceive; // RECEIVING AMOUNT

        String receivingParty = allocationKey.getReceivingParty().getLabel(); // RECEIVING PARTY LABEL

        try {

            // COMPUTE RECEIVING AMOUNT
            if (receivingParty.equals("POURVOYEURS DE FONDS")) {

                amountToReceive = minimumAmount + ((percentage * profitabilityAmount) / 100);

            } else {

                amountToReceive = ((percentage * profitabilityAmount) / 100);

            }

            allocationKey.setReceivingAmount(amountToReceive); // SET AMOUNT TO RECEIVE

            return allocationKeyRepository.save(allocationKey); // SAVE

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }

    // ADD IN ALLOCATION KEY LIST
    private void addToAllocationKeyList(MutualInvestment mutualInvestment) {

        // POURVOYEUR DE FONDS
        mutualInvestment.getAllocationKeys().add(generateAllocationKeys(mutualInvestment.getMinimumAmount(), mutualInvestment.getProfitabilityAmount(), 50L, 1L));

        // CAUTIONS
        mutualInvestment.getAllocationKeys().add(generateAllocationKeys(mutualInvestment.getMinimumAmount(), mutualInvestment.getProfitabilityAmount(), 10L, 2L));

        // MUTUELLE
        mutualInvestment.getAllocationKeys().add(generateAllocationKeys(mutualInvestment.getMinimumAmount(), mutualInvestment.getProfitabilityAmount(), 30L, 3L));

        // FONDS DE REVENUS PASSIFS
        mutualInvestment.getAllocationKeys().add(generateAllocationKeys(mutualInvestment.getMinimumAmount(), mutualInvestment.getProfitabilityAmount(), 10L, 4L));
    }

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
}
