package com.b2i.activitiesorganisation.dto.request.SubscriptionPayment;

import java.time.LocalDate;

public class SubscriptionPaymentRequest {

    private LocalDate date;

    private Long paid;

    private String proof;

    public SubscriptionPaymentRequest() {
    }

    public SubscriptionPaymentRequest(LocalDate date, Long paid, String proof) {
        this.date = date;
        this.paid = paid;
        this.proof = proof;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }
}
