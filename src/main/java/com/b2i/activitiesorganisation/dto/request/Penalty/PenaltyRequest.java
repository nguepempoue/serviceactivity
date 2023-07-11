package com.b2i.activitiesorganisation.dto.request.Penalty;

import java.time.LocalDate;

public class PenaltyRequest {

    private LocalDate date;

    public PenaltyRequest() {

    }

    public PenaltyRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PenaltyRequest{" +
                ", date=" + date +
                '}';
    }
}
