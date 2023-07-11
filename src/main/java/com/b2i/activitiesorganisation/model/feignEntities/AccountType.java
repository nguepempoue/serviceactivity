package com.b2i.activitiesorganisation.model.feignEntities;

public class AccountType {

    private Long id;

    private String label;

    private String description;

    private Double amount;

    public AccountType(Long id, String label, String description, Double amount) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.amount = amount;
    }

    public AccountType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
