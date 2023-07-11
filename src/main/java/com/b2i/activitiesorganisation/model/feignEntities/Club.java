package com.b2i.activitiesorganisation.model.feignEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Embeddable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Club {

    private Long id;

    private String name;

    private String reference;

    public Club() {

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
