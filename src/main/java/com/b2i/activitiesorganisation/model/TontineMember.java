package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.model.feignEntities.User;

import javax.persistence.*;

@Entity
@Table(name = "tontine_member")
public class TontineMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tontineName;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "participant_id"))
    private User participant;

    private Long userPlan;

    public TontineMember() {

    }

    public TontineMember(String tontineName, User participant, Long userPlan) {
        this.tontineName = tontineName;
        this.participant = participant;
        this.userPlan = userPlan;
    }

    public String getTontineName() {
        return tontineName;
    }

    public void setTontineName(String tontineName) {
        this.tontineName = tontineName;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public Long getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(Long userPlan) {
        this.userPlan = userPlan;
    }

    @Override
    public String toString() {
        return "TontineMember{" +
                "id=" + id +
                ", tontineName='" + tontineName + '\'' +
                ", participant=" + participant +
                ", userPlan=" + userPlan +
                '}';
    }
}
