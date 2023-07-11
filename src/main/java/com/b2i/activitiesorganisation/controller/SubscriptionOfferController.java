package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.SubscriptionOffer.SubscriptionOfferRequest;
import com.b2i.activitiesorganisation.service.SubscriptionOffer.SubscriptionOfferServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
public class SubscriptionOfferController {


    @Autowired
    private SubscriptionOfferServiceImplementation subscriptionOfferServiceImplementation;


    // FIND ALL
    @GetMapping
    public ResponseEntity<Object> findAll() {
        return subscriptionOfferServiceImplementation.findAll();
    }


    // UPDATE
    @PutMapping("/{idOffer}")
    public ResponseEntity<Object> updateSubscriptionOffer(@PathVariable("idOffer") Long idOffer, @RequestBody SubscriptionOfferRequest subscriptionOfferRequest) {
        return subscriptionOfferServiceImplementation.updateSubscriptionOffer(idOffer, subscriptionOfferRequest);
    }


    // DELETE SUBSCRIPTION OFFER
    @DeleteMapping("/{idOffer}")
    public ResponseEntity<Object> deleteSubscriptionOffer(@PathVariable("idOffer") Long idOffer) {
        return subscriptionOfferServiceImplementation.deleteSubscriptionOffer(idOffer);
    }


    // FIND BY ID
    @GetMapping("/{idOffer}")
    public ResponseEntity<Object> findSubscriptionOfferById(@PathVariable("idOffer") Long idOffer) {
        return subscriptionOfferServiceImplementation.findSubscriptionOfferById(idOffer);
    }
}
