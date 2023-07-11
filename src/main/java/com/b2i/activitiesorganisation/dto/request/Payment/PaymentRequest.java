package com.b2i.activitiesorganisation.dto.request.Payment;

import java.time.LocalDate;

public class PaymentRequest {

    private LocalDate date;

    private Long paid;

    private String proof;

    public PaymentRequest() {

    }

    public PaymentRequest(LocalDate date, Long paid, String proof) {
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

    @Override
    public String toString() {
        return "PaymentRequest{" +
                ", date=" + date +
                ", paid=" + paid +
                ", proof='" + proof + '\'' +
                '}';
    }
}
