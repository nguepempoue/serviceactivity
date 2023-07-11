package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "refund_type")
public class RefundType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public RefundType() {

    }

    public RefundType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RefundType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
