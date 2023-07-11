package com.b2i.activitiesorganisation.dto.request.SecurityDeposit;

public class SecurityDepositRequest {

    private Long amount;

    public SecurityDepositRequest() {

    }

    public SecurityDepositRequest(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SecurityDepositRequest{" +
                ", amount=" + amount +
                '}';
    }
}
