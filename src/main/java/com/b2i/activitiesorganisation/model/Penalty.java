package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "penalty")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Boolean paid = false;

    @ManyToOne
    private PenaltyType penaltyType;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private User user;

    public Penalty() {

    }

    public Penalty(LocalDate date, Boolean paid, PenaltyType penaltyType, User user) {
        this.date = date;
        this.paid = paid;
        this.penaltyType = penaltyType;
        this.user = user;
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

    public PenaltyType getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(PenaltyType penaltyType) {
        this.penaltyType = penaltyType;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Penalty{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", penaltyType=" + penaltyType +
                ", paid=" + paid +
                '}';
    }
}
