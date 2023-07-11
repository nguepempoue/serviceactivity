package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.model.feignEntities.Account;
import com.b2i.activitiesorganisation.model.feignEntities.Club;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tontine")
public class Tontine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long PEB;

    private Long registeredMembers = 0L;

    private Long membersWithoutPenalties = 0L;

    private Long awardedLots = 0L;

    private Long lotsToBeAwarded = 0L;

    private Long collectedAmount = 0L;

    private Long collectedPenalties = 0L;

    private Long durationInMonths = 0L;

    private Long sessionsNumber = 0L;

    @Column(columnDefinition = "text")
    private String observation = "";

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "club_id")),
            @AttributeOverride(name = "name", column = @Column(name = "club_name")),
            @AttributeOverride(name = "reference", column = @Column(name = "club_reference"))
    })
    private Club clubOwner;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "tontine_account_id"))
    private Account account;

    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // @ElementCollection
    // private Set<Long> usersId = new HashSet<>();

    @OneToMany
    private Set<TontineMember> tontineMembers = new HashSet<>();

    private Boolean isTransversal = false;

    @ManyToOne
    private TransversalityLevel level;

    @ManyToOne
    private Frequency contributionFrequency;

    @ManyToOne
    private Frequency tontineSessionFrequency;

    @ManyToOne
    private GainMode gainMode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cycle> cycles = new ArrayList<>();

    @ManyToOne
    private Status status;

    public Tontine() {

    }

    public Tontine(String name, Long PEB, Long durationInMonths, String observation, Club clubOwner, Account account) {
        this.name = name;
        this.PEB = PEB;
        this.durationInMonths = durationInMonths;
        this.observation = observation;
        this.clubOwner = clubOwner;
        this.account = account;
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

    public Long getPEB() {
        return PEB;
    }

    public void setPEB(Long PEB) {
        this.PEB = PEB;
    }

    public Long getRegisteredMembers() {
        return registeredMembers;
    }

    public void setRegisteredMembers(Long registeredMembers) {
        this.registeredMembers = registeredMembers;
    }

    public Long getMembersWithoutPenalties() {
        return membersWithoutPenalties;
    }

    public void setMembersWithoutPenalties(Long membersWithoutPenalties) {
        this.membersWithoutPenalties = membersWithoutPenalties;
    }

    public Long getAwardedLots() {
        return awardedLots;
    }

    public void setAwardedLots(Long awardedLots) {
        this.awardedLots = awardedLots;
    }

    public Long getLotsToBeAwarded() {
        return lotsToBeAwarded;
    }

    public void setLotsToBeAwarded(Long lotsToBeAwarded) {
        this.lotsToBeAwarded = lotsToBeAwarded;
    }

    public Long getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Long collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public Long getCollectedPenalties() {
        return collectedPenalties;
    }

    public void setCollectedPenalties(Long collectedPenalties) {
        this.collectedPenalties = collectedPenalties;
    }

    public Long getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(Long durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public Boolean getTransversal() {
        return isTransversal;
    }

    public void setTransversal(Boolean transversal) {
        isTransversal = transversal;
    }

    public TransversalityLevel getLevel() {
        return level;
    }

    public void setLevel(TransversalityLevel level) {
        this.level = level;
    }

    public Frequency getContributionFrequency() {
        return contributionFrequency;
    }

    public void setContributionFrequency(Frequency contributionFrequency) {
        this.contributionFrequency = contributionFrequency;
    }

    public Frequency getTontineSessionFrequency() {
        return tontineSessionFrequency;
    }

    public void setTontineSessionFrequency(Frequency tontineSessionFrequency) {
        this.tontineSessionFrequency = tontineSessionFrequency;
    }

    public GainMode getGainMode() {
        return gainMode;
    }

    public void setGainMode(GainMode gainMode) {
        this.gainMode = gainMode;
    }

    public List<Cycle> getCycles() {
        return cycles;
    }

    public void setCycles(List<Cycle> cycles) {
        this.cycles = cycles;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getSessionsNumber() {
        return sessionsNumber;
    }

    public void setSessionsNumber(Long sessionsNumber) {
        this.sessionsNumber = sessionsNumber;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Club getClubOwner() {
        return clubOwner;
    }

    public void setClubOwner(Club clubOwner) {
        this.clubOwner = clubOwner;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<TontineMember> getTontineMembers() {
        return tontineMembers;
    }

    public void setTontineMembers(Set<TontineMember> tontineMembers) {
        this.tontineMembers = tontineMembers;
    }

    @Override
    public String toString() {
        return "Tontine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", PEB=" + PEB +
                ", registeredMembers=" + registeredMembers +
                ", membersWithoutPenalties=" + membersWithoutPenalties +
                ", awardedLots=" + awardedLots +
                ", lotsToBeAwarded=" + lotsToBeAwarded +
                ", collectedAmount=" + collectedAmount +
                ", collectedPenalties=" + collectedPenalties +
                ", durationInMonths=" + durationInMonths +
                ", sessionsNumber=" + sessionsNumber +
                ", observation='" + observation + '\'' +
                ", isTransversal=" + isTransversal +
                ", level=" + level +
                ", contributionFrequency=" + contributionFrequency +
                ", tontineSessionFrequency=" + tontineSessionFrequency +
                ", gainMode=" + gainMode +
                ", cycles=" + cycles +
                ", status=" + status +
                '}';
    }
}
