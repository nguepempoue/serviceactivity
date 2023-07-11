package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "payment_status")
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    public PaymentStatus() {
    }

    public PaymentStatus(String label) {
        this.label = label;
    }

    public PaymentStatus(Long id, String label) {
        this.id = id;
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
        return "PaymentStatus{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
