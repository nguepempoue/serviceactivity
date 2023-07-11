package com.b2i.activitiesorganisation.dto.request.PaymentStatus;

public class PaymentStatusRequest {

    private String label;

    public PaymentStatusRequest() {

    }

    public PaymentStatusRequest(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "PaymentStatusRequest{" +
                ", label='" + label + '\'' +
                '}';
    }
}
