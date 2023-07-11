package com.b2i.activitiesorganisation.dto.request.Tontine;

public class TontineRequest {

    private String name;

    private Long PEB;

    private Long durationInMonths;

    private String observation;

    public TontineRequest() {

    }

    public TontineRequest(String name, Long PEB, Long durationInMonths) {
        this.name = name;
        this.PEB = PEB;
        this.durationInMonths = durationInMonths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPEB() {
        return PEB;
    }

    public void setPEB(Long PEB) {
        this.PEB = PEB;
    }

    public Long getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(Long durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "TontineRequest{" +
                ", name='" + name + '\'' +
                ", PEB=" + PEB +
                ", durationInMonths=" + durationInMonths +
                ", observation='" + observation + '\'' +
                '}';
    }
}
