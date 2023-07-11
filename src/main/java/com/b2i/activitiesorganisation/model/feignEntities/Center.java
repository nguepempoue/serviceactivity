package com.b2i.activitiesorganisation.model.feignEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Center {

    private Long id;

    @Column(name = "center_name")
    private String name;

    public Center() {

    }

    public Center(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Center{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
