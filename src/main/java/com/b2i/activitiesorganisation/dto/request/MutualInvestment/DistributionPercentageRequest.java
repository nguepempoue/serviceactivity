package com.b2i.activitiesorganisation.dto.request.MutualInvestment;

public class DistributionPercentageRequest {

    private Long percentageOfFunders;

    private Long percentageOfGuarantees;

    private Long percentageMutual;

    private Long percentageOfPassiveIncomeFund;

    public DistributionPercentageRequest() {
    }

    public DistributionPercentageRequest(Long percentageOfFunders, Long percentageOfGuarantees, Long percentageMutual, Long percentageOfPassiveIncomeFund) {
        this.percentageOfFunders = percentageOfFunders;
        this.percentageOfGuarantees = percentageOfGuarantees;
        this.percentageMutual = percentageMutual;
        this.percentageOfPassiveIncomeFund = percentageOfPassiveIncomeFund;
    }

    public Long getPercentageOfFunders() {
        return percentageOfFunders;
    }

    public void setPercentageOfFunders(Long percentageOfFunders) {
        this.percentageOfFunders = percentageOfFunders;
    }

    public Long getPercentageOfGuarantees() {
        return percentageOfGuarantees;
    }

    public void setPercentageOfGuarantees(Long percentageOfGuarantees) {
        this.percentageOfGuarantees = percentageOfGuarantees;
    }

    public Long getPercentageMutual() {
        return percentageMutual;
    }

    public void setPercentageMutual(Long percentageMutual) {
        this.percentageMutual = percentageMutual;
    }

    public Long getPercentageOfPassiveIncomeFund() {
        return percentageOfPassiveIncomeFund;
    }

    public void setPercentageOfPassiveIncomeFund(Long percentageOfPassiveIncomeFund) {
        this.percentageOfPassiveIncomeFund = percentageOfPassiveIncomeFund;
    }
}
