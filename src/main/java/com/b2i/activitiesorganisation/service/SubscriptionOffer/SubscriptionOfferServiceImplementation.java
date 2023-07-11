package com.b2i.activitiesorganisation.service.SubscriptionOffer;

import com.b2i.activitiesorganisation.Utils.Utils;
import com.b2i.activitiesorganisation.constant.MutualInvesmentEnum;
import com.b2i.activitiesorganisation.dto.request.SubscriptionOffer.SubscriptionOfferRequest;
import com.b2i.activitiesorganisation.dto.response.ResponseHandler;
import com.b2i.activitiesorganisation.model.MutualInvestment;
import com.b2i.activitiesorganisation.model.ProfitabilityType;
import com.b2i.activitiesorganisation.model.RiskProfile;
import com.b2i.activitiesorganisation.model.SubscriptionOffer;
import com.b2i.activitiesorganisation.repository.MutualInvestmentRepository;
import com.b2i.activitiesorganisation.repository.ProfitabilityTypeRepository;
import com.b2i.activitiesorganisation.repository.RiskProfileRepository;
import com.b2i.activitiesorganisation.repository.SubscriptionOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionOfferServiceImplementation implements SubscriptionOfferService {

    @Autowired
    private SubscriptionOfferRepository subscriptionOfferRepository;

    @Autowired
    private RiskProfileRepository riskProfileRepository;

    @Autowired
    private ProfitabilityTypeRepository profitabilityTypeRepository;

    @Autowired
    private MutualInvestmentRepository mutualInvestmentRepository;


    // CREATE
    @Override
    public ResponseEntity<Object> createSubscriptionOffer(String token, Long idInvestment, Long idRiskProfile, Long idProfitabilityType, Long profitabilityRate) {

        // GET RISK PROFILE
        Optional<RiskProfile> riskProfile = riskProfileRepository.findById(idRiskProfile);

        // GET PROFITABILITY TYPE
        Optional<ProfitabilityType> profitabilityType = profitabilityTypeRepository.findById(idProfitabilityType);

        // GET MUTUAL INVESTMENT
        Optional<MutualInvestment> mutualInvestment = mutualInvestmentRepository.findById(idInvestment);

        try {

            return mutualInvestment.map(i -> {

                if (i.getStatus() == MutualInvesmentEnum.CLOSE) {
                    return ResponseHandler.generateNotFoundResponse("Ce placement mutualisé a déjà été fermé, vous ne pouvez plus y effectuer d'opération !");
                }

                // RISK PROFILE
                if(!riskProfile.isPresent()) {
                    return ResponseHandler.generateNotFoundResponse("Risk profile not found !");
                }

                // PROFITABILITY TYPE
                if(!profitabilityType.isPresent()) {
                    return ResponseHandler.generateNotFoundResponse("Profitability type not found !");
                }

                // NEW SUBSCRIPTION OFFER
                SubscriptionOffer offer = new SubscriptionOffer();

                // IF PROFITABILITY IS CERTAIN
                if(profitabilityType.get().getLabel().equals("CERTAIN")) {
                    try {
                        Utils.checkLongValues(profitabilityRate, "Profitability rate"); // VERIFY PROFITABILITY RATE

                        if(i.getPercentageOfFunders() <= 0L) {
                            throw new Exception("The percentage of funders has not yet been recorded. !");
                        }

                        if(profitabilityRate > i.getPercentageOfFunders()) {
                            throw new Exception("the rate of return on the offer cannot be higher than the rate reserved for the funders !");
                        }
                        offer.setProfitabilityRate(profitabilityRate); // SAVE PROFITABILITY RATE IF PROFITABILITY IS CERTAIN
                    } catch (Exception e) {
                        return Utils.catchException(e);
                    }
                }

                offer.setProfitabilityType(profitabilityType.get());
                offer.setRiskProfile(riskProfile.get());

                i.getOffers().add(subscriptionOfferRepository.save(offer));

                // SAVE MUTUAL INVESTMENT
                return ResponseHandler.generateCreatedResponse("Offer has been properly created !",
                        mutualInvestmentRepository.save(i));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Mutual investment not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND ALL
    @Override
    public ResponseEntity<Object> findAll() {

        // GET ALL
        List<SubscriptionOffer> offerList = subscriptionOfferRepository.findAll();

        try {

            if(offerList.isEmpty()) {
                return ResponseHandler.generateNoContentResponse("Empty list !");
            }

            return ResponseHandler.generateOkResponse("Offer list", offerList);
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // UPDATE
    @Override
    public ResponseEntity<Object> updateSubscriptionOffer(Long idOffer, SubscriptionOfferRequest subscriptionOfferRequest) {

        // GET OFFER
        Optional<SubscriptionOffer> offer = subscriptionOfferRepository.findById(idOffer);

        try {

            return offer.map(o -> {

                if(subscriptionOfferRequest.getProfitabilityRate() != null && subscriptionOfferRequest.getProfitabilityRate() != 0L) {
                    o.setProfitabilityRate(subscriptionOfferRequest.getProfitabilityRate());
                }

                return ResponseHandler.generateOkResponse("Offer has been properly updated !",
                        subscriptionOfferRepository.save(o));

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Offer not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // DELETE SUBSCRIPTION OFFER
    @Override
    public ResponseEntity<Object> deleteSubscriptionOffer(Long idOffer) {

        // GET OFFER
        Optional<SubscriptionOffer> offer = subscriptionOfferRepository.findById(idOffer);

        try {

            return offer.map(o -> {

                // GET INVESTMENT OF OFFER
                MutualInvestment investment = new MutualInvestment();
                for(MutualInvestment i : mutualInvestmentRepository.findAll()) {

                    // IF INVESTMENT CONTAINS OFFER, GET INVESTMENT
                    if(i.getOffers().contains(o)) {
                        investment = i;
                        break;
                    }
                }

                // OFFER WILL BE REMOVED FROM OFFERS OF INVESTMENT
                investment.getOffers().remove(o);
                mutualInvestmentRepository.save(investment);

                // DELETE OFFER BY ID
                subscriptionOfferRepository.deleteById(idOffer);
                return ResponseHandler.generateOkResponse("Offer has properly been deleted !",
                        null);

            }).orElseGet(() -> ResponseHandler.generateNotFoundResponse("Offer not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }


    // FIND BY ID
    @Override
    public ResponseEntity<Object> findSubscriptionOfferById(Long idOffer) {

        // GET OFFER
        Optional<SubscriptionOffer> offer = subscriptionOfferRepository.findById(idOffer);

        try {

            return offer.map(o -> ResponseHandler.generateOkResponse("Offer " + idOffer, o))
                    .orElseGet(() -> ResponseHandler.generateNotFoundResponse("Offer not found !"));
        }
        catch (Exception e) {
            return Utils.catchException(e);
        }
    }
}
