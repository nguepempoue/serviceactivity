package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.SubscriptionOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionOfferRepository extends JpaRepository<SubscriptionOffer, Long> {

    @Query("select so from SubscriptionOffer so join so.subscriptions s where s.id=:idSubscription")
    SubscriptionOffer findSubscriptionOfferByIdSubscription(Long idSubscription);
}
