package com.b2i.activitiesorganisation.dto.request.Frequency;

public class FrequencyRequest {

    private String label;

    private String description;

    public FrequencyRequest() {

    }

    public FrequencyRequest(String label, String description) {
        this.label = label;
        this.description = description;
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
        return "FrequencyRequest{" +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
