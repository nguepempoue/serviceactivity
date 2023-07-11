package com.b2i.activitiesorganisation.dto.request.PaymentMethod;

public class PaymentMethodRequest {

    private String label;

    public PaymentMethodRequest() {

    }

    public PaymentMethodRequest(String label) {
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
        return "PaymentMethodRequest{" +
                ", label='" + label + '\'' +
                '}';
    }
}
