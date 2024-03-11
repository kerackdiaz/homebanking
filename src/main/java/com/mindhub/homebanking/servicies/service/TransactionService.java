package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.dtos.TransferDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public String transfer(TransferDTO transferDTO, String userMail) {
        try {
            Client clientOrigin = clientRepository.findByEmail(userMail);
            Client clientDestination = clientRepository.findByAccountsNumber(transferDTO.accountDestination());
            Account origin = accountRepository.findByNumber(transferDTO.accountOrigin());
            Account destination = accountRepository.findByNumber(transferDTO.accountDestination());

            if (!clientOrigin.getAccounts().stream().anyMatch(a -> a.getNumber().equals(transferDTO.accountOrigin()))) {
                return "account not found";
            }

            if (transferDTO.accountOrigin().equals(transferDTO.accountDestination())) {
                return "select another account";
            }

            if (!accountRepository.existsByNumber(transferDTO.accountDestination())) {
                return "account not found";
            }

            if (transferDTO.amount() <= 0) {
                return "transfer amount must be positive";
            }

            if (!origin.getClient().equals(clientOrigin)) {
                return "unauthorized transfer";
            }

            if (origin.getBalance() < transferDTO.amount()) {
                return "insufficient funds";
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

            return "success";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }
}