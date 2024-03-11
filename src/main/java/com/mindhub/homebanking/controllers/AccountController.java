package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.servicies.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public ResponseEntity<List<AccountsDTO>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts().stream()
                .map(AccountsDTO::new)
                .collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") Long id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            String notData = "Data not found";
            return new ResponseEntity<>(notData, HttpStatus.NOT_FOUND);
        }
        AccountsDTO accountsDTO = new AccountsDTO(account);
        return new ResponseEntity<>(accountsDTO, HttpStatus.OK);
    }

    @PostMapping("/current/account")
    public ResponseEntity<?> createAccount(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(accountService.createAccount(userMail));
    }

    @GetMapping("/current/account")
    public ResponseEntity<?> getAccounts(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(accountService.getAccounts(userMail).stream().map(AccountsDTO::new).collect(Collectors.toList()));
    }
}
