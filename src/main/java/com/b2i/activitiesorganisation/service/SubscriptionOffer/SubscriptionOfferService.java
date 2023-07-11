package com.b2i.activitiesorganisation.service.SubscriptionOffer;

import com.b2i.activitiesorganisation.dto.request.SubscriptionOffer.SubscriptionOfferRequest;
import org.springframework.http.ResponseEntity;

public interface SubscriptionOfferService {

    // CRUD OPERATIONS //
   ResponseEntity<Object> createSubscriptionOffer(String token, Long idInvestment, Long idRiskProfile, Long idProfitabilityType, Long profitabilityRate);

   ResponseEntity<Object> findAll();

   ResponseEntity<Object> updateSubscriptionOffer(Long idOffer, SubscriptionOfferRequest subscriptionOfferRequest);

   ResponseEntity<Object> deleteSubscriptionOffer(Long idOffer);


   // MORE OPERATIONS //
    ResponseEntity<Object> findSubscriptionOfferById(Long idOffer);
}
