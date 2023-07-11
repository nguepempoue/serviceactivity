package com.b2i.activitiesorganisation.model;

import java.time.LocalDate;

public class FirstRefundDate {
    private LocalDate date;

    public FirstRefundDate() {
    }

    public FirstRefundDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
