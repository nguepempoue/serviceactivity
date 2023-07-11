package com.b2i.activitiesorganisation.dto.request.Form;

public class UserRequest {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String city;

    private String phoneNumber;

    private String email;

    public UserRequest(String firstName, String lastName, String userName, String city, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
