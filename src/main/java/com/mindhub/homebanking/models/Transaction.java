package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private TransactionType type;
    private String description;
    private LocalDateTime dateTime;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "Client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    public Transaction() {
    }

    public Transaction(TransactionType type, String description, LocalDateTime dateTime, double amount) {
        this.type = type;
        this.description = description;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }


    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransaction(Account account) {
    }
}
