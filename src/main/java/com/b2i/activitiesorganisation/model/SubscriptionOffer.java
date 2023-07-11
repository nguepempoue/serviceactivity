package com.b2i.activitiesorganisation.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscription_offer")
public class SubscriptionOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RiskProfile riskProfile;

    @ManyToOne
    private ProfitabilityType profitabilityType;

    @ManyToOne
    private MutualInvestment mutualInvestment;

    private Long profitabilityRate = 0L;

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    public SubscriptionOffer() {

    }

    public SubscriptionOffer(Long profitabilityRate) {
        this.profitabilityRate = profitabilityRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public ProfitabilityType getProfitabilityType() {
        return profitabilityType;
    }

    public void setProfitabilityType(ProfitabilityType profitabilityType) {
        this.profitabilityType = profitabilityType;
    }

    public MutualInvestment getMutualInvestment() {
        return mutualInvestment;
    }

    public void setMutualInvestment(MutualInvestment mutualInvestment) {
        this.mutualInvestment = mutualInvestment;
    }

    public Long getProfitabilityRate() {
        return profitabilityRate;
    }

    public void setProfitabilityRate(Long profitabilityRate) {
        this.profitabilityRate = profitabilityRate;
    }

    @Override
    public String toString() {
        return "SubscriptionOffer{" +
                "id=" + id +
                ", riskProfile=" + riskProfile +
                ", profitabilityType=" + profitabilityType +
                ", profitabilityRate=" + profitabilityRate +
                '}';
    }
}
