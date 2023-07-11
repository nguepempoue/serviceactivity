package com.b2i.activitiesorganisation.dto.request.ReceivingParty;

public class ReceivingPartyRequest {

    private String label;

    public ReceivingPartyRequest(String label) {
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
        return "ReceivingPartyRequest{" +
                ", label='" + label + '\'' +
                '}';
    }
}
