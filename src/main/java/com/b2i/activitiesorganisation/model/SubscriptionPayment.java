package com.b2i.activitiesorganisation.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscription_payment")
public class SubscriptionPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Long paid;

    @Column(columnDefinition = "text")
    private String proof;

    @ManyToOne
    private PaymentMethod paymentMethod;

    public SubscriptionPayment() {
    }

    public SubscriptionPayment(Long id, LocalDate date, Long paid, String proof, PaymentMethod paymentMethod) {
        this.id = id;
        this.date = date;
        this.paid = paid;
        this.proof = proof;
        this.paymentMethod = paymentMethod;
    }


    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
