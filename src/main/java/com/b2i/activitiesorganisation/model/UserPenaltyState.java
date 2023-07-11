package com.b2i.activitiesorganisation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_penalty_state")
public class UserPenaltyState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    private String userName;

    private Long penaltiesToPay = 0L;

    private Long paidPenalties = 0L;

    private Long remainingToPay = 0L;

    private Boolean upToDate = true;

    @OneToMany
    private List<Payment> paymentList = new ArrayList<>();

    public UserPenaltyState() {

    }

    public UserPenaltyState(Long userId, String userName, Long penaltiesToPay, Long paidPenalties, Long remainingToPay, Boolean upToDate) {
        this.userId = userId;
        this.userName = userName;
        this.penaltiesToPay = penaltiesToPay;
        this.paidPenalties = paidPenalties;
        this.remainingToPay = remainingToPay;
        this.upToDate = upToDate;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPenaltiesToPay() {
        return penaltiesToPay;
    }

    public void setPenaltiesToPay(Long penaltiesToPay) {
        this.penaltiesToPay = penaltiesToPay;
    }

    public Long getPaidPenalties() {
        return paidPenalties;
    }

    public void setPaidPenalties(Long paidPenalties) {
        this.paidPenalties = paidPenalties;
    }

    public Long getRemainingToPay() {
        return remainingToPay;
    }

    public void setRemainingToPay(Long remainingToPay) {
        this.remainingToPay = remainingToPay;
    }

    public Boolean getUpToDate() {
        return upToDate;
    }

    public void setUpToDate(Boolean upToDate) {
        this.upToDate = upToDate;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    @Override
    public String toString() {
        return "UserPenaltyState{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", penaltiesToPay=" + penaltiesToPay +
                ", paidPenalties=" + paidPenalties +
                ", remainingToPay=" + remainingToPay +
                ", upToDate=" + upToDate +
                ", paymentList=" + paymentList +
                '}';
    }
}
