package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime hour;

    private LocalDate contributionDeadline;

    private Long totalPaid = 0L;

    private Long totalToBePaid = 0L;

    private Long totalPenalties = 0L;

    @ManyToOne
    private Status status;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "winner_id"))
    private User winner;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Penalty> penalties = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserPaymentState> userPaymentStates = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserPenaltyState> userPenaltyStates = new ArrayList<>();

    public Session() {

    }

    public Session(LocalDate date, LocalTime hour, LocalDate contributionDeadline) {
        this.date = date;
        this.hour = hour;
        this.contributionDeadline = contributionDeadline;
    }

    public Session(Long id, LocalDate date, LocalTime hour, LocalDate contributionDeadline, Long totalPaid,
                   Long totalToBePaid, Long totalPenalties) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.contributionDeadline = contributionDeadline;
        this.totalPaid = totalPaid;
        this.totalToBePaid = totalToBePaid;
        this.totalPenalties = totalPenalties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getContributionDeadline() {
        return contributionDeadline;
    }

    public void setContributionDeadline(LocalDate contributionDeadline) {
        this.contributionDeadline = contributionDeadline;
    }

    public Long getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Long totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Long getTotalToBePaid() {
        return totalToBePaid;
    }

    public void setTotalToBePaid(Long totalToBePaid) {
        this.totalToBePaid = totalToBePaid;
    }

    public Long getTotalPenalties() {
        return totalPenalties;
    }

    public void setTotalPenalties(Long totalPenalties) {
        this.totalPenalties = totalPenalties;
    }

    public Set<Penalty> getPenalties() {
        return penalties;
    }

    public void setPenalties(Set<Penalty> penalties) {
        this.penalties = penalties;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public List<UserPaymentState> getUserPaymentStates() {
        return userPaymentStates;
    }

    public void setUserPaymentStates(List<UserPaymentState> userPaymentStates) {
        this.userPaymentStates = userPaymentStates;
    }

    public List<UserPenaltyState> getUserPenaltyStates() {
        return userPenaltyStates;
    }

    public void setUserPenaltyStates(List<UserPenaltyState> userPenaltyStates) {
        this.userPenaltyStates = userPenaltyStates;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", date=" + date +
                ", hour=" + hour +
                ", contributionDeadline=" + contributionDeadline +
                ", totalPaid=" + totalPaid +
                ", totalToBePaid=" + totalToBePaid +
                ", totalPenalties=" + totalPenalties +
                ", status=" + status +
                ", winner=" + winner +
                ", penalties=" + penalties +
                ", payments=" + payments +
                ", userPaymentStates=" + userPaymentStates +
                ", userPenaltyStates=" + userPenaltyStates +
                '}';
    }
}
