package com.b2i.activitiesorganisation.dto.request.Subscription;

public class SubscriptionRequest {
    private Long amount;

    public SubscriptionRequest() {
    }

    public SubscriptionRequest(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SubscriptionRequest{" +
                "amount=" + amount +
                '}';
    }
}
