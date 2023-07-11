package com.b2i.activitiesorganisation.model;

import javax.persistence.*;

@Entity
@Table(name = "frequency")
public class Frequency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    private String description;

    public Frequency() {

    }

    public Frequency(Long id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Frequency{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
