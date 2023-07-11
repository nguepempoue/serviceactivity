package com.b2i.activitiesorganisation.model.feignEntities;

import javax.persistence.Embeddable;

@Embeddable
public class MutOrganism {

    private String organismName;

    private String firstNameRepre;

    private String lastNameRepre;

    private String userNameRepre;

    private String cityRepre;

    private String phoneNumberRepre;

    private String emailRepre;

    public MutOrganism() {
    }

    public MutOrganism(String organismName, String firstNameRepre, String lastNameRepre, String userNameRepre, String cityRepre, String phoneNumberRepre, String emailRepre) {
        this.organismName = organismName;
        this.firstNameRepre = firstNameRepre;
        this.lastNameRepre = lastNameRepre;
        this.userNameRepre = userNameRepre;
        this.cityRepre = cityRepre;
        this.phoneNumberRepre = phoneNumberRepre;
        this.emailRepre = emailRepre;
    }

    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    public String getFirstNameRepre() {
        return firstNameRepre;
    }

    public void setFirstNameRepre(String firstNameRepre) {
        this.firstNameRepre = firstNameRepre;
    }

    public String getLastNameRepre() {
        return lastNameRepre;
    }

    public void setLastNameRepre(String lastNameRepre) {
        this.lastNameRepre = lastNameRepre;
    }

    public String getUserNameRepre() {
        return userNameRepre;
    }

    public void setUserNameRepre(String userNameRepre) {
        this.userNameRepre = userNameRepre;
    }

    public String getCityRepre() {
        return cityRepre;
    }

    public void setCityRepre(String cityRepre) {
        this.cityRepre = cityRepre;
    }

    public String getPhoneNumberRepre() {
        return phoneNumberRepre;
    }

    public void setPhoneNumberRepre(String phoneNumberRepre) {
        this.phoneNumberRepre = phoneNumberRepre;
    }

    public String getEmailRepre() {
        return emailRepre;
    }

    public void setEmailRepre(String emailRepre) {
        this.emailRepre = emailRepre;
    }
}
