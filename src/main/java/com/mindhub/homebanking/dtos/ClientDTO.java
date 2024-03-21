package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.*;
import jakarta.persistence.*;
import com.mindhub.homebanking.models.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private String profilePictureUrl;
    private List<AccountsDTO> accounts;

    private List<TransactionDTO> transactions;

    private List<ClientLoanDTO> loans;

    private List<CardDTO> Card;


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.profilePictureUrl = client.getImage() != null ? client.getImage() : "http://localhost:5173/public/pp-ulver-bank.jpg";
        this.accounts = accountsDTO(client.getAccounts());
        this.transactions= transactionDTOS(client.getTransactions());
        this.loans = clientLoanDTO(client.getLoans());
        this.Card = cardDTO(client.getCard());
    }

    private List<CardDTO> cardDTO(List<Card> cards) {
        return cards.stream().map(CardDTO::new).toList();
    }

    private List<ClientLoanDTO> clientLoanDTO(List<ClientLoan> loans) {
        return loans.stream().map(ClientLoanDTO::new).toList();
    }

    private List<TransactionDTO> transactionDTOS(List<Transaction> transactions){
        return transactions.stream().map(TransactionDTO::new).toList();
    }

    private List<AccountsDTO> accountsDTO(List<Account> accounts) {
        return accounts.stream().map(AccountsDTO::new).toList();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePictureUrl() {

        return this.profilePictureUrl;
    }

    public List<AccountsDTO> getAccounts() {
        return accounts;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public List<CardDTO> getCard() {
        return Card;
    }
}
