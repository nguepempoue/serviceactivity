package com.b2i.activitiesorganisation.service.Subscription;

import com.b2i.activitiesorganisation.dto.request.Subscription.SubscriptionRequest;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    ResponseEntity<Object> subscribe(String token, SubscriptionRequest subscriptionRequest, Long idSubscriber, Long idSubscriptionOffer);

    ResponseEntity<Object> update(SubscriptionRequest subscriptionRequest, Long idSubscrition);

    ResponseEntity<Object> deleteSubscription(Long idSubscrition);

    ResponseEntity<Object> findSubscriptionById(Long idSubscrition);

    ResponseEntity<Object> releaseSubscription(String token, Long idSouscription);

    ResponseEntity<Object> findAllSubscriptions();
}
