package com.b2i.activitiesorganisation.dto.request.ProfitabilityType;

public class ProfitabilityTypeRequest {

    private String label;

    public ProfitabilityTypeRequest() {

    }

    public ProfitabilityTypeRequest(String label) {
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
        return "ProfitabilityTypeRequest{" +
                ", label='" + label + '\'' +
                '}';
    }
}
