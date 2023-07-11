package com.b2i.activitiesorganisation.dto.request.AmountCollected;

import java.time.LocalDate;

public class AmountCollectedRequest {

    private LocalDate date;

    private Long paid;

    private String proof;

    public AmountCollectedRequest() {
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
