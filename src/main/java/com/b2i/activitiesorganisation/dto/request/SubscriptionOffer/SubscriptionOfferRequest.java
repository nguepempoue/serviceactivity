package com.b2i.activitiesorganisation.dto.request.SubscriptionOffer;

public class SubscriptionOfferRequest {

    private Long profitabilityRate;

    public SubscriptionOfferRequest() {

    }

    public SubscriptionOfferRequest(Long profitabilityRate) {
        this.profitabilityRate = profitabilityRate;
    }

    public Long getProfitabilityRate() {
        return profitabilityRate;
    }

    public void setProfitabilityRate(Long profitabilityRate) {
        this.profitabilityRate = profitabilityRate;
    }

    @Override
    public String toString() {
        return "SubscriptionOfferRequest{" +
                "profitabilityRate=" + profitabilityRate +
                '}';
    }
}
