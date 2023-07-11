package com.b2i.activitiesorganisation.dto.request.Session;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionRequest {

    private LocalDate date;

    private LocalTime hour;

    private LocalDate contributionDeadline;

    public SessionRequest() {

    }

    public SessionRequest(LocalDate date, LocalTime hour, LocalDate contributionDeadline) {
        this.date = date;
        this.hour = hour;
        this.contributionDeadline = contributionDeadline;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getContributionDeadline() {
        return contributionDeadline;
    }

    public void setContributionDeadline(LocalDate contributionDeadline) {
        this.contributionDeadline = contributionDeadline;
    }

    @Override
    public String toString() {
        return "SessionRequest{" +
                ", date=" + date +
                ", hour=" + hour +
                ", contributionDeadline=" + contributionDeadline +
                '}';
    }
}
