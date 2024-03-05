package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransferDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Transactional
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

@PostMapping("/transfer")
public ResponseEntity<?> transfer(@RequestBody TransferDTO transferDTO) {
    try {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client clientOrigin = clientRepository.findByEmail(userMail);
        Client clientDestination = clientRepository.findByAccountsNumber(transferDTO.accountDestination());
        Account origin = accountRepository.findByNumber(transferDTO.accountOrigin());
        Account destination = accountRepository.findByNumber(transferDTO.accountDestination());

        if (!clientOrigin.getAccounts().stream().anyMatch(a -> a.getNumber().equals(transferDTO.accountOrigin()))) {
            return ResponseEntity.badRequest().body("account not found");
        }

        if (transferDTO.accountOrigin() == transferDTO.accountDestination()) {
            return ResponseEntity.badRequest().body("select another account");
        }
        if (!accountRepository.existsByNumber(transferDTO.accountDestination())) {
            return ResponseEntity.badRequest().body("account not found");
        }
        if (origin.getBalance() < transferDTO.amount()) {
            return ResponseEntity.badRequest().body("insufficient funds");
        }
        origin.setBalance(origin.getBalance() - transferDTO.amount());
        destination.setBalance(destination.getBalance() + transferDTO.amount());

        accountRepository.save(origin);
        accountRepository.save(destination);

        Transaction transactionOrigin = new Transaction(TransactionType.DEBIT, transferDTO.description(), LocalDateTime.now(), -transferDTO.amount());
        transactionOrigin.setAccount(origin);
        transactionOrigin.setClient(clientOrigin);
        transactionRepository.save(transactionOrigin);

        Transaction transactionDestination = new Transaction(TransactionType.CREDIT, transferDTO.description(), LocalDateTime.now(), transferDTO.amount());
        transactionDestination.setAccount(destination);
        transactionDestination.setClient(clientDestination);
        transactionRepository.save(transactionDestination);

        return ResponseEntity.ok("success");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}

    @GetMapping("/reception")
    public void reception() {
    }
}
