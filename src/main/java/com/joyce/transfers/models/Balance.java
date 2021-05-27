package com.joyce.transfers.models;

import javax.persistence.*;

@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Long account;

    @Column(nullable = false)
    private Long balance;

    public Balance() {
    }

    public Balance ( long account, long balance) {
        this.account = account;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", account=" + account +
                ", balance=" + balance +
                '}';
    }
}
