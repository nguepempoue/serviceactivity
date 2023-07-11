package com.b2i.activitiesorganisation.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cycle")
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long lotAmount;

    @ManyToOne
    private Status status;

    private Long echeanceNumber;

    private Long numberOfSessionPassed = 0L;

    private Long numberOfSessionRemaining = 0L;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Session> sessions = new ArrayList<>();

    @ElementCollection
    private List<Long> sessionWinnersId = new ArrayList<>();

    public Cycle() {

    }

    public Cycle(String name, LocalDate startDate, Long lotAmount, LocalDate endDate, Long echeanceNumber, List<Session> sessions) {
        this.name = name;
        this.startDate = startDate;
        this.lotAmount = lotAmount;
        this.endDate = endDate;
        this.echeanceNumber = echeanceNumber;
        this.sessions = sessions;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getLotAmount() {
        return lotAmount;
    }

    public void setLotAmount(Long lotAmount) {
        this.lotAmount = lotAmount;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getEcheanceNumber() {
        return echeanceNumber;
    }

    public void setEcheanceNumber(Long echeanceNumber) {
        this.echeanceNumber = echeanceNumber;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getNumberOfSessionPassed() {
        return numberOfSessionPassed;
    }

    public void setNumberOfSessionPassed(Long numberOfSessionPassed) {
        this.numberOfSessionPassed = numberOfSessionPassed;
    }

    public Long getNumberOfSessionRemaining() {
        return numberOfSessionRemaining;
    }

    public void setNumberOfSessionRemaining(Long numberOfSessionRemaining) {
        this.numberOfSessionRemaining = numberOfSessionRemaining;
    }

    public List<Long> getSessionWinnersId() {
        return sessionWinnersId;
    }

    public void setSessionWinnersId(List<Long> sessionWinnersId) {
        this.sessionWinnersId = sessionWinnersId;
    }

    @Override
    public String toString() {
        return "Cycle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lotAmount=" + lotAmount +
                ", status=" + status +
                ", echeanceNumber=" + echeanceNumber +
                ", numberOfSessionPassed=" + numberOfSessionPassed +
                ", numberOfSessionRemaining=" + numberOfSessionRemaining +
                ", sessions=" + sessions +
                '}';
    }
}
