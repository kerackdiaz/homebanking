package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @Column(unique=true)
    private String email;

    private String password;


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Account> accounts;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoan> loans;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Card> card;

    public Client() {
    }


    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static void setLoan(Loan loan) {
    }


    public long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        if ((accounts == null)) {
            accounts = new ArrayList<>();
        }
        account.setClient(this);
        accounts.add(account);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<ClientLoan> getLoans() {
        return loans;
    }

    public void setLoans(List<ClientLoan> loans) {
        this.loans = loans;
    }

    public List<Card> getCard() {
        return card;
    }

    public void setCard(List<Card> card) {
        this.card = card;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" + "id= " + id + ", Name= " + firstName + ' ' + lastName + "\n" + ", email='" + email + "\n" + ", Accounts= " + accounts + '}';
    }
}

