package com.b2i.activitiesorganisation.model;

import com.b2i.activitiesorganisation.model.feignEntities.Account;

import javax.persistence.*;

@Entity
@Table(name = "passive_income_fund_account")
public class PassiveIncomeFundAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "account_id"))
    private Account account;

    public PassiveIncomeFundAccount() {
    }

    public PassiveIncomeFundAccount(Long id, Account account) {
        this.id = id;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
