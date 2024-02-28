package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<List<AccountsDTO>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return new ResponseEntity<>(accounts.stream()
                .map(account -> new AccountsDTO(account))
                .collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            String notData = "Data not found";
            return new ResponseEntity<>(notData, HttpStatus.NOT_FOUND);
        }
        AccountsDTO accountsDTO = new AccountsDTO(account);
        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }

}
