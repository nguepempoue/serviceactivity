package com.b2i.activitiesorganisation.dto.request.RefundType;

public class RefundTypeRequest {

    private String type;

    public RefundTypeRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RefundTypeRequest{" +
                ", type='" + type + '\'' +
                '}';
    }
}
