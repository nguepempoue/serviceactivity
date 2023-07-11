package com.b2i.activitiesorganisation.dto.request.Form;

public class OrganismRequest extends UserRequest{

    private String organismName;


    public OrganismRequest(String firstName, String lastName, String userName, String city, String phoneNumber, String email, String organismName) {
        super(firstName, lastName, userName, city, phoneNumber, email);
        this.organismName = organismName;
    }

    public OrganismRequest(String organismName) {
        this.organismName = organismName;
    }

    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }
}
