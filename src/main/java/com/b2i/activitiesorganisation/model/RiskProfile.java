package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "risk_profile")
public class RiskProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long riskLevel = 0L;

    public RiskProfile() {

    }

    public RiskProfile(Long riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Long riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String toString() {
        return "RiskProfile{" +
                "id=" + id +
                ", riskLevel=" + riskLevel +
                '}';
    }
}
