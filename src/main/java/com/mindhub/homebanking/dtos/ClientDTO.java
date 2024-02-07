package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientDTO {
    private  long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountsDTO> accounts;

    private List<ClientLoanDTO> loans;


    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts=accountsDTO(client.getAccounts());
        this.loans=clientLoanDTO(client.getLoans());
    }

    private List<ClientLoanDTO> clientLoanDTO(List<ClientLoan> loans) {
        return loans.stream().map(ClientLoanDTO::new).toList();
    }

    private List<AccountsDTO>accountsDTO(List<Account>accounts){
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

    public List<AccountsDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

}
