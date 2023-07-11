package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "penalty_type")
public class PenaltyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    private Long amount;

    public PenaltyType() {
    }

    public PenaltyType(String label, Long amount) {
        this.label = label;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PenaltyType{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", amount=" + amount +
                '}';
    }
}
