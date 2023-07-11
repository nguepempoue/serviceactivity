package com.b2i.activitiesorganisation.dto.request.MutualInvestment;

import com.b2i.activitiesorganisation.dto.request.Form.OrganismRequest;
import com.b2i.activitiesorganisation.dto.request.Form.UserRequest;

import java.time.LocalDate;

public class MutualInvestmentRequest {

    private String name;

    private Long minimumAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long profitabilityRate;

    private Long echeanceDurationInMonths;

    private UserRequest physicalPerson;

    private OrganismRequest mutOrganism;

//    private Long percentageOfFunders;
//
//    private Long percentageOfGuarantees;
//
//    private Long percentageMutual;
//
//    private Long percentageOfPassiveIncomeFund;

    public MutualInvestmentRequest() {

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Long minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getProfitabilityRate() {
        return profitabilityRate;
    }

    public void setProfitabilityRate(Long profitabilityRate) {
        this.profitabilityRate = profitabilityRate;
    }

    public Long getEcheanceDurationInMonths() {
        return echeanceDurationInMonths;
    }

//    public Long getPercentageOfFunders() {
//        return percentageOfFunders;
//    }
//
//    public void setPercentageOfFunders(Long percentageOfFunders) {
//        this.percentageOfFunders = percentageOfFunders;
//    }
//
//    public Long getPercentageOfGuarantees() {
//        return percentageOfGuarantees;
//    }
//
//    public void setPercentageOfGuarantees(Long percentageOfGuarantees) {
//        this.percentageOfGuarantees = percentageOfGuarantees;
//    }
//
//    public Long getPercentageMutual() {
//        return percentageMutual;
//    }
//
//    public void setPercentageMutual(Long percentageMutual) {
//        this.percentageMutual = percentageMutual;
//    }
//
//    public Long getPercentageOfPassiveIncomeFund() {
//        return percentageOfPassiveIncomeFund;
//    }
//
//    public void setPercentageOfPassiveIncomeFund(Long percentageOfPassiveIncomeFund) {
//        this.percentageOfPassiveIncomeFund = percentageOfPassiveIncomeFund;
//    }

    public void setEcheanceDurationInMonths(Long echeanceDurationInMonths) {
        this.echeanceDurationInMonths = echeanceDurationInMonths;
    }

    public UserRequest getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(UserRequest physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public OrganismRequest getMutOrganism() {
        return mutOrganism;
    }

    public void setMutOrganism(OrganismRequest mutOrganism) {
        this.mutOrganism = mutOrganism;
    }

    @Override
    public String toString() {
        return "MutualInvestmentRequest{" +
                "name='" + name + '\'' +
                ", minimumAmount=" + minimumAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", profitabilityRate=" + profitabilityRate +
                ", echeanceDurationInMonths=" + echeanceDurationInMonths +
                ", physicalPerson=" + physicalPerson +
                ", organism=" + mutOrganism +
                '}';
    }

}
