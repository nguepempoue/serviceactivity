package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private Long amountPaid;

    private PaymentEnum status;

    private Long amountOfProfitability = 0L;

    private Long totalAmountReceivable = 0L;

    private Long totalAmountReceived = 0L;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "subscription_user_id"))
    private User subscriber;

    @ManyToOne
    private RiskProfile riskProfile;

    @ManyToOne
    private SubscriptionOffer subscriptionOffer;

    public List<SubscriptionPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<SubscriptionPayment> payments) {
        this.payments = payments;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubscriptionPayment> payments;


    public Subscription() {
    }

    public Subscription(Long id, Long amount, User subscriber, RiskProfile riskProfile) {
        this.id = id;
        this.amount = amount;
        this.subscriber = subscriber;
        this.riskProfile = riskProfile;
    }

    public Long getAmountOfProfitability() {
        return amountOfProfitability;
    }

    public void setAmountOfProfitability(Long amountOfProfitability) {
        this.amountOfProfitability = amountOfProfitability;
    }

    public Long getTotalAmountReceivable() {
        return totalAmountReceivable;
    }

    public void setTotalAmountReceivable(Long totalAmountReceivable) {
        this.totalAmountReceivable = totalAmountReceivable;
    }

    public Long getTotalAmountReceived() {
        return totalAmountReceived;
    }

    public void setTotalAmountReceived(Long totalAmountReceived) {
        this.totalAmountReceived = totalAmountReceived;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public PaymentEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentEnum status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public SubscriptionOffer getSubscriptionOffer() {
        return subscriptionOffer;
    }

    public void setSubscriptionOffer(SubscriptionOffer subscriptionOffer) {
        this.subscriptionOffer = subscriptionOffer;
    }
}
