package com.b2i.activitiesorganisation.dto.request.Proposal;

public class ProposalRequest {

    private Long interestPercentage;

    private Long interestValue;

    public ProposalRequest(Long interestPercentage, Long interestValue) {
        this.interestPercentage = interestPercentage;
        this.interestValue = interestValue;
    }

    public Long getInterestPercentage() {
        return interestPercentage;
    }

    public void setInterestPercentage(Long interestPercentage) {
        this.interestPercentage = interestPercentage;
    }

    public Long getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(Long interestValue) {
        this.interestValue = interestValue;
    }

    @Override
    public String toString() {
        return "ProposalRequest{" +
                ", interestPercentage=" + interestPercentage +
                ", interestValue=" + interestValue +
                '}';
    }
}
