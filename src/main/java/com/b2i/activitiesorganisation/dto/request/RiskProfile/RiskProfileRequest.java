package com.b2i.activitiesorganisation.dto.request.RiskProfile;

public class RiskProfileRequest {

    private Long riskLevel = 0L;

    public RiskProfileRequest() {

    }

    public RiskProfileRequest(Long riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Long getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Long riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String toString() {
        return "RiskProfileRequest{" +
                ", riskLevel=" + riskLevel +
                '}';
    }
}
