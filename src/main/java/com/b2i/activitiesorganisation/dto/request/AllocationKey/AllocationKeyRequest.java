package com.b2i.activitiesorganisation.dto.request.AllocationKey;

public class AllocationKeyRequest {

    private Long percentage;

    public AllocationKeyRequest() {

    }

    public AllocationKeyRequest(Long percentage) {
        this.percentage = percentage;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "AllocationKeyRequest{" +
                ", percentage=" + percentage +
                '}';
    }
}
