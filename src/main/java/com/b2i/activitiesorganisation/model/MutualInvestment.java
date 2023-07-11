package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.constant.DistributionEnum;
import com.b2i.activitiesorganisation.constant.MutualInvesmentEnum;
import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.model.feignEntities.Center;
import com.b2i.activitiesorganisation.model.feignEntities.MutOrganism;
import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mutual_investment")
public class MutualInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //private String organism = "";

    //private Long rating = 0L;

    private Long minimumAmount = 0L;

    private LocalDate startDate = LocalDate.now();

    private LocalDate endDate = LocalDate.now();

    private Long profitabilityRate = 0L;

    private Long profitabilityAmount = 0L;

    private Long echeanceDurationInMonths = 0L;

    private Long securityDepositUsersNumber = 0L;

    private Long collectedAmount = 0L;

    private Long amountToBeRefunded = 0L;

    private Long amountRefunded = 0L;

    private MutualInvesmentEnum mutualInvesmentStatus;

    private MutualInvesmentEnum status;

    private PaymentEnum RefundStatus;

    private DistributionEnum distributionStatus;

    private PaymentEnum RefundDateStatus;

    private Long percentageOfFunders;

    private Long percentageOfGuarantees;

    private Long percentageMutual;

    private Long percentageOfPassiveIncomeFund;

    @ManyToOne
    private DraweeForm draweeForm;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private User user;

    public void setOrganism(MutOrganism mutOrganism) {
        this.mutOrganism = mutOrganism;
    }

    @Embedded
    //@AttributeOverride(name = "id", column = @Column(name = "organism_id"))
    private MutOrganism mutOrganism;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "mutual_investment_account_id"))
    private Account mutualInvestmentAccount;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "center_id"))
    private Center mutualCenter;

    @ManyToOne
    private RefundType refundType;

    @ManyToOne
    private Frequency refundFrequency;

    /*@ElementCollection
    private List<LocalDate> refundDates = new ArrayList<>();*/

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Refund> refunds = new ArrayList<>();

    @ManyToOne
    private ProfitabilityType profitabilityType;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AllocationKey> allocationKeys = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    private List<SecurityDeposit> securityDeposits = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<SubscriptionOffer> offers = new ArrayList<>();

    public MutualInvestment() {

    }

    public MutualInvestment(String name, Long minimumAmount) {
        this.name = name;
       // this.rating = rating;
        this.minimumAmount = minimumAmount;
    }

    public MutualInvestment(Long id, String name, Long minimumAmount, LocalDate startDate, LocalDate endDate, Long profitabilityRate, Long profitabilityAmount, Long echeanceDurationInMonths, Long securityDepositUsersNumber, Long collectedAmount, Long amountToBeRefunded, Long amountRefunded, MutualInvesmentEnum mutualInvesmentStatus, PaymentEnum refundStatus, DistributionEnum distributionStatus, PaymentEnum refundDateStatus, Long percentageOfFunders, Long percentageOfGuarantees, Long percentageMutual, Long percentageOfPassiveIncomeFund, DraweeForm draweeForm, User user, MutOrganism mutOrganism, Account mutualInvestmentAccount, Center mutualCenter, RefundType refundType, Frequency refundFrequency, List<Refund> refunds, ProfitabilityType profitabilityType, List<AllocationKey> allocationKeys, List<SecurityDeposit> securityDeposits, List<SubscriptionOffer> offers) {
        this.id = id;
        this.name = name;
        this.minimumAmount = minimumAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.profitabilityRate = profitabilityRate;
        this.profitabilityAmount = profitabilityAmount;
        this.echeanceDurationInMonths = echeanceDurationInMonths;
        this.securityDepositUsersNumber = securityDepositUsersNumber;
        this.collectedAmount = collectedAmount;
        this.amountToBeRefunded = amountToBeRefunded;
        this.amountRefunded = amountRefunded;
        this.mutualInvesmentStatus = mutualInvesmentStatus;
        RefundStatus = refundStatus;
        this.distributionStatus = distributionStatus;
        RefundDateStatus = refundDateStatus;
        this.percentageOfFunders = percentageOfFunders;
        this.percentageOfGuarantees = percentageOfGuarantees;
        this.percentageMutual = percentageMutual;
        this.percentageOfPassiveIncomeFund = percentageOfPassiveIncomeFund;
        this.draweeForm = draweeForm;
        this.user = user;
        this.mutOrganism = mutOrganism;
        this.mutualInvestmentAccount = mutualInvestmentAccount;
        this.mutualCenter = mutualCenter;
        this.refundType = refundType;
        this.refundFrequency = refundFrequency;
        this.refunds = refunds;
        this.profitabilityType = profitabilityType;
        this.allocationKeys = allocationKeys;
        this.securityDeposits = securityDeposits;
        this.offers = offers;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getProfitabilityAmount() {
        return profitabilityAmount;
    }

    public void setProfitabilityAmount(Long profitabilityAmount) {
        this.profitabilityAmount = profitabilityAmount;
    }

    public Long getEcheanceDurationInMonths() {
        return echeanceDurationInMonths;
    }

    public void setEcheanceDurationInMonths(Long echeanceDurationInMonths) {
        this.echeanceDurationInMonths = echeanceDurationInMonths;
    }

    public Long getSecurityDepositUsersNumber() {
        return securityDepositUsersNumber;
    }

    public void setSecurityDepositUsersNumber(Long securityDepositUsersNumber) {
        this.securityDepositUsersNumber = securityDepositUsersNumber;
    }

    public Long getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Long collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public Long getAmountToBeRefunded() {
        return amountToBeRefunded;
    }

    public void setAmountToBeRefunded(Long amountToBeRefunded) {
        this.amountToBeRefunded = amountToBeRefunded;
    }

    public Long getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Long amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public MutualInvesmentEnum getMutualInvesmentStatus() {
        return mutualInvesmentStatus;
    }

    public void setMutualInvesmentStatus(MutualInvesmentEnum mutualInvesmentStatus) {
        this.mutualInvesmentStatus = mutualInvesmentStatus;
    }

    public PaymentEnum getRefundStatus() {
        return RefundStatus;
    }

    public void setRefundStatus(PaymentEnum refundStatus) {
        RefundStatus = refundStatus;
    }

    public DistributionEnum getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(DistributionEnum distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public PaymentEnum getRefundDateStatus() {
        return RefundDateStatus;
    }

    public void setRefundDateStatus(PaymentEnum refundDateStatus) {
        RefundDateStatus = refundDateStatus;
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

    public DraweeForm getDraweeForm() {
        return draweeForm;
    }

    public void setDraweeForm(DraweeForm draweeForm) {
        this.draweeForm = draweeForm;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MutOrganism getOrganism() {
        return mutOrganism;
    }

    public Account getMutualInvestmentAccount() {
        return mutualInvestmentAccount;
    }

    public void setMutualInvestmentAccount(Account mutualInvestmentAccount) {
        this.mutualInvestmentAccount = mutualInvestmentAccount;
    }

    public Center getMutualCenter() {
        return mutualCenter;
    }

    public void setMutualCenter(Center mutualCenter) {
        this.mutualCenter = mutualCenter;
    }

    public RefundType getRefundType() {
        return refundType;
    }

    public void setRefundType(RefundType refundType) {
        this.refundType = refundType;
    }

    public Frequency getRefundFrequency() {
        return refundFrequency;
    }

    public void setRefundFrequency(Frequency refundFrequency) {
        this.refundFrequency = refundFrequency;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public ProfitabilityType getProfitabilityType() {
        return profitabilityType;
    }

    public void setProfitabilityType(ProfitabilityType profitabilityType) {
        this.profitabilityType = profitabilityType;
    }

    public List<AllocationKey> getAllocationKeys() {
        return allocationKeys;
    }

    public void setAllocationKeys(List<AllocationKey> allocationKeys) {
        this.allocationKeys = allocationKeys;
    }

    public List<SecurityDeposit> getSecurityDeposits() {
        return securityDeposits;
    }

    public void setSecurityDeposits(List<SecurityDeposit> securityDeposits) {
        this.securityDeposits = securityDeposits;
    }

    public List<SubscriptionOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<SubscriptionOffer> offers) {
        this.offers = offers;
    }

    public MutualInvesmentEnum getStatus() {
        return status;
    }

    public void setStatus(MutualInvesmentEnum status) {
        this.status = status;
    }

    public MutOrganism getMutOrganism() {
        return mutOrganism;
    }

    public void setMutOrganism(MutOrganism mutOrganism) {
        this.mutOrganism = mutOrganism;
    }

    @Override
    public String toString() {
        return "MutualInvestment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minimumAmount=" + minimumAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", profitabilityRate=" + profitabilityRate +
                ", profitabilityAmount=" + profitabilityAmount +
                ", echeanceDurationInMonths=" + echeanceDurationInMonths +
                ", securityDepositUsersNumber=" + securityDepositUsersNumber +
                ", collectedAmount=" + collectedAmount +
                ", amountToBeRefunded=" + amountToBeRefunded +
                ", amountRefunded=" + amountRefunded +
                ", mutualInvesmentStatus=" + mutualInvesmentStatus +
                ", RefundStatus=" + RefundStatus +
                ", distributionStatus=" + distributionStatus +
                ", RefundDateStatus=" + RefundDateStatus +
                ", percentageOfFunders=" + percentageOfFunders +
                ", percentageOfGuarantees=" + percentageOfGuarantees +
                ", percentageMutual=" + percentageMutual +
                ", percentageOfPassiveIncomeFund=" + percentageOfPassiveIncomeFund +
                ", draweeForm=" + draweeForm +
                ", user=" + user +
                ", organism=" + mutOrganism +
                ", mutualInvestmentAccount=" + mutualInvestmentAccount +
                ", mutualCenter=" + mutualCenter +
                ", refundType=" + refundType +
                ", refundFrequency=" + refundFrequency +
                ", refunds=" + refunds +
                ", profitabilityType=" + profitabilityType +
                ", allocationKeys=" + allocationKeys +
                ", securityDeposits=" + securityDeposits +
                ", offers=" + offers +
                '}';
    }
}
