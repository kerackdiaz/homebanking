package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionDTO {
    private long id;
    private long accountId;
    private TransactionType type;
    private String description;
    private LocalDateTime dateTime;
    private double amount;


    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.accountId = transaction.getAccount().getId();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.dateTime = transaction.getDateTime();
        this.amount = transaction.getAmount();
    }


    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return dateTime.format(formatter);
        }
        return "";
    }

    public double getAmount() {
        return amount;
    }
}
