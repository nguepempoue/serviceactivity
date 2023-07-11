package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "allocation_key")
public class AllocationKey {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ReceivingParty receivingParty;

    private Long percentage;

    private Long receivingAmount;

    public Long getAdjustedAmount() {
        return adjustedAmount;
    }

    public void setAdjustedAmount(Long adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    public Long getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(Long amountReceived) {
        this.amountReceived = amountReceived;
    }

    private Long adjustedAmount = 0L;

    private Long amountReceived = 0L;

    public AllocationKey() {

    }

    public AllocationKey(ReceivingParty receivingParty, Long percentage) {
        this.receivingParty = receivingParty;
        this.percentage = percentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReceivingParty getReceivingParty() {
        return receivingParty;
    }

    public void setReceivingParty(ReceivingParty receivingParty) {
        this.receivingParty = receivingParty;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    public Long getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(Long receivingAmount) {
        this.receivingAmount = receivingAmount;
    }



}
