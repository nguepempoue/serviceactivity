package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "security_deposit")
public class SecurityDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount = 0L;

    private Long amountPaid = 0L;

    private Long amountToPay = 0L;

    private Long amountReceived = 0L;

    private PaymentEnum RefundStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RefundAmount> refundAmounts = new ArrayList<>();

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "deposit_user_id"))
    private User depositUser;

    public SecurityDeposit() {

    }

    public SecurityDeposit(Long amount) {
        this.amount = amount;
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

    public User getDepositUser() {
        return depositUser;
    }

    public void setDepositUser(User depositUser) {
        this.depositUser = depositUser;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Long getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(Long amountReceived) {
        this.amountReceived = amountReceived;
    }

    public Long getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Long amountToPay) {
        this.amountToPay = amountToPay;
    }

    public PaymentEnum getRefundStatus() {
        return RefundStatus;
    }

    public void setRefundStatus(PaymentEnum refundStatus) {
        RefundStatus = refundStatus;
    }

    public List<RefundAmount> getRefundAmounts() {
        return refundAmounts;
    }

    public void setRefundAmounts(List<RefundAmount> refundAmounts) {
        this.refundAmounts = refundAmounts;
    }

    @Override
    public String toString() {
        return "SecurityDeposit{" +
                "id=" + id +
                ", amount=" + amount +
                ", depositUser=" + depositUser +
                '}';
    }
}
