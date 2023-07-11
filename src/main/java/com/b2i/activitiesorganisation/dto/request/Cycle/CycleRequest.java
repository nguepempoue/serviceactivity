package com.b2i.activitiesorganisation.dto.request.Cycle;

import java.time.LocalDate;

public class CycleRequest {

    private String name;

    private LocalDate startDate;

    public CycleRequest() {

    }

    public CycleRequest(String name, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
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

    @Override
    public String toString() {
        return "CycleRequest{" +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
