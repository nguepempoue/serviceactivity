package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String userFullName;

    private Long interestPercentage;

    private Long interestValue;

    private Long amountToReceive;

    public Proposal(Long userId, String userFullName, Long interestPercentage, Long interestValue, Long amountToReceive) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.interestPercentage = interestPercentage;
        this.interestValue = interestValue;
        this.amountToReceive = amountToReceive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Long getInterestPercentage() {
        return interestPercentage;
    }

    public void setInterestPercentage(Long interestPercentage) {
        this.interestPercentage = interestPercentage;
    }

    public Long getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(Long interestValue) {
        this.interestValue = interestValue;
    }

    public Long getAmountToReceive() {
        return amountToReceive;
    }

    public void setAmountToReceive(Long amountToReceive) {
        this.amountToReceive = amountToReceive;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", userId=" + userId +
                ", userFullName='" + userFullName + '\'' +
                ", interestPercentage=" + interestPercentage +
                ", interestValue=" + interestValue +
                ", amountToReceive=" + amountToReceive +
                '}';
    }
}
