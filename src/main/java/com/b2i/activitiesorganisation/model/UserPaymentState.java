package com.b2i.activitiesorganisation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_payment_state")
public class UserPaymentState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    private String userName;

    private Long toBePaidByUser = 0L;

    private Long remainingToPayByUser = 0L;

    private Boolean upToDate = false;

    @OneToMany
    private List<Payment> userPayments = new ArrayList<>();

    public UserPaymentState() {

    }

    public UserPaymentState(Long userId, String userName, Long toBePaidByUser, Long remainingToPayByUser, Boolean upToDate) {
        this.userId = userId;
        this.userName = userName;
        this.toBePaidByUser = toBePaidByUser;
        this.remainingToPayByUser = remainingToPayByUser;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getToBePaidByUser() {
        return toBePaidByUser;
    }

    public void setToBePaidByUser(Long toBePaidByUser) {
        this.toBePaidByUser = toBePaidByUser;
    }

    public Long getRemainingToPayByUser() {
        return remainingToPayByUser;
    }

    public void setRemainingToPayByUser(Long remainingToPayByUser) {
        this.remainingToPayByUser = remainingToPayByUser;
    }

    public Boolean getUpToDate() {
        return upToDate;
    }

    public void setUpToDate(Boolean upToDate) {
        this.upToDate = upToDate;
    }

    public List<Payment> getUserPayments() {
        return userPayments;
    }

    public void setUserPayments(List<Payment> userPayments) {
        this.userPayments = userPayments;
    }

    @Override
    public String toString() {
        return "UserPaymentState{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPayments=" + userPayments +
                ", toBePaidByUser=" + toBePaidByUser +
                ", remainingToPayByUser=" + remainingToPayByUser +
                ", upToDate=" + upToDate +
                '}';
    }
}
