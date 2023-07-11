package com.b2i.activitiesorganisation.dto.request.PenaltyType;

public class PenaltyTypeRequest {

    private String label;

    private Long amount;

    public PenaltyTypeRequest() {

    }

    public PenaltyTypeRequest(String label, Long amount) {
        this.label = label;
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PenaltyTypeRequest{" +
                ", label='" + label + '\'' +
                ", amount=" + amount +
                '}';
    }
}
