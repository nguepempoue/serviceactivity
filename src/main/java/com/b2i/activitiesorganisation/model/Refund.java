package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.constant.PaymentEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "refund")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amountToBeRefunded = 0L;

    private Long amountRefunded = 0L;

    private LocalDate refundDate = LocalDate.now();

    private PaymentEnum RefundStatus;


    @OneToMany(cascade = CascadeType.ALL)
    private List<AmountCollected> amountCollecteds = new ArrayList<>();

    public Refund() {
    }

    public Refund(Long id, Long amountToBeRefunded, Long amountRefunded, LocalDate refundDate) {
        this.id = id;
        this.amountToBeRefunded = amountToBeRefunded;
        this.amountRefunded = amountRefunded;
        this.refundDate = refundDate;
    }

    public List<AmountCollected> getAmountCollecteds() {
        return amountCollecteds;
    }

    public void setAmountCollecteds(List<AmountCollected> amountCollecteds) {
        this.amountCollecteds = amountCollecteds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmountToBeRefunded() {
        return amountToBeRefunded;
    }

    public void setAmountToBeRefunded(Long amountToBeRefunded) {
        this.amountToBeRefunded = amountToBeRefunded;
    }

    public Long getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Long amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }

    public PaymentEnum getRefundStatus() {
        return RefundStatus;
    }

    public void setRefundStatus(PaymentEnum refundStatus) {
        RefundStatus = refundStatus;
    }
}
