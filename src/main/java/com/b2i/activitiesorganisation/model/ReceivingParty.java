package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "receiving_party")
public class ReceivingParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    public ReceivingParty() {

    }

    public ReceivingParty(String label) {
        this.label = label;
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

    @Override
    public String toString() {
        return "ReceivingParty{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
