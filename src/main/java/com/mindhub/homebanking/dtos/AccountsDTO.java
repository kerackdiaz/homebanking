package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import java.time.LocalDate;
import java.util.List;

public class AccountsDTO {
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;

    private List<Transaction> transactionList;


    public AccountsDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
//        this.transactionList = account.getTransactions();
    }

    private List<TransactionDTO> accountsDTO(List<Transaction> transactions) {
        return transactions.stream().map(TransactionDTO::new).toList();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

//    public List<Transaction> getTransactionList() {
//        return transactionList;
//    }
}
