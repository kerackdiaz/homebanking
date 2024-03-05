package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utilitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public ResponseEntity<List<AccountsDTO>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return new ResponseEntity<>(accounts.stream()
                .map(AccountsDTO::new)
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


    @PostMapping("/current/account")
    public ResponseEntity<?> createAccount(){

        String accountNumber = RandomUtil.generateAccountNumber(8);
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Already has 3 accounts", HttpStatus.BAD_REQUEST);
        }

        while (accountRepository.existsByNumber(accountNumber)) {
            accountNumber = RandomUtil.generateAccountNumber(8);
        }

        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0);
        newAccount.setClient(client);
        accountRepository.save(newAccount);

        return ResponseEntity.ok("Account created successfully!");
    }


    @GetMapping("/current/accounts")
    public ResponseEntity<?> getAccounts(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        return ResponseEntity.ok(client.getAccounts().stream().map(AccountsDTO::new).collect(Collectors.toList()));
    }

}
