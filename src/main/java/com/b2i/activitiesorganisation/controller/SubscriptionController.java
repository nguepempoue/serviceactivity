package com.b2i.activitiesorganisation.controller;

import com.b2i.activitiesorganisation.dto.request.Subscription.SubscriptionRequest;
import com.b2i.activitiesorganisation.dto.request.SubscriptionPayment.SubscriptionPaymentRequest;
import com.b2i.activitiesorganisation.service.Subscription.SubscriptionServiceImplement;
import com.b2i.activitiesorganisation.service.SubscriptionPayment.SubscriptionPaymentServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin("*")
public class SubscriptionController {

    private final SubscriptionServiceImplement subscriptionServiceImplement;

    private final SubscriptionPaymentServiceImplementation subscriptionPaymentServiceImplementation;

    public SubscriptionController(SubscriptionServiceImplement subscriptionServiceImplement, SubscriptionPaymentServiceImplementation subscriptionPaymentServiceImplementation) {
        this.subscriptionServiceImplement = subscriptionServiceImplement;
        this.subscriptionPaymentServiceImplementation = subscriptionPaymentServiceImplementation;
    }

    // FIND ALL SUBSCRIPTIONS
    @GetMapping
    public ResponseEntity<Object> findAllSubscriptionsPayments() {
        return subscriptionServiceImplement.findAllSubscriptions();
    }

    // CREATE A SUBSCRIPTION
    @PostMapping("/subscription-offer/{idSubscriptionOffer}/subscriber/{idSubscriber}")
    public ResponseEntity<Object> createSubscription(@RequestBody SubscriptionRequest subscriptionRequest, @PathVariable("idSubscriber") Long idSubscriber, @PathVariable("idSubscriptionOffer") Long idSubscriptionOffer, @RequestParam String token) {
        return subscriptionServiceImplement.subscribe(token, subscriptionRequest, idSubscriber, idSubscriptionOffer);
    }

    // UPDATE SUBSCRIPTION
    @PutMapping("/{idSubscription}")
    public ResponseEntity<Object> updateSubscriptionPayment(@PathVariable("idSubscription") Long idSubscription, @RequestBody SubscriptionRequest subscriptionRequest) {
        return subscriptionServiceImplement.update(subscriptionRequest, idSubscription);
    }


    // DELETE SUBSCRIPTION
    @DeleteMapping("/{idSubscription}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable("idSubscription") Long idSubscription) {
        return subscriptionServiceImplement.deleteSubscription(idSubscription);
    }


    // FIND SUBSCRIPTION BY ID
    @GetMapping("/{idSubscription}")
    public ResponseEntity<Object> findSubscriptionById(@PathVariable("idSubscription") Long idSubscription) {
        return subscriptionServiceImplement.findSubscriptionById(idSubscription);
    }
    // CRAETE PAYEMENT FOR SUBSCRIPTION
    @PostMapping("/subscription-payment/{idSubscription}/payment-method/{idPaymentMethod}")
    public ResponseEntity<Object> createPaymentForSubscription(@PathVariable("idSubscription") Long idSubscription,@PathVariable("idPaymentMethod") Long idPaymentMethod,@RequestBody SubscriptionPaymentRequest subscriptionPaymentRequest, @RequestParam String token) {
        return subscriptionPaymentServiceImplementation.createPayment(token, idSubscription, idPaymentMethod, subscriptionPaymentRequest);
    }

    // RELEASE A SUBSCRIPTION
    @GetMapping("/release/{idSubscription}")
    public ResponseEntity<Object> releaseSubscription(@PathVariable("idSubscription") Long idSubscription, @RequestParam String token) {
        return subscriptionServiceImplement.releaseSubscription(token, idSubscription);
    }
}
