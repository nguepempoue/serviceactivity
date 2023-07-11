package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private User user;

    private Long paid;

    @Column(columnDefinition = "text")
    private String proof;

    @ManyToOne
    private PaymentStatus paymentStatus;

    @ManyToOne
    private PaymentMethod paymentMethod;

    public Payment() {

    }

    public Payment(LocalDate date, User user, Long paid, String proof, PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
        this.date = date;
        this.user = user;
        this.paid = paid;
        this.proof = proof;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", paid=" + paid +
                ", proof='" + proof + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
