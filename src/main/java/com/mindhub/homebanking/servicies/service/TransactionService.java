package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.dtos.TransferDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> transfer(TransferDTO transferDTO, String userMail) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client clientOrigin = clientRepository.findByEmail(userMail);
            Client clientDestination = clientRepository.findByAccountsNumber(transferDTO.accountDestination());
            Account origin = accountRepository.findByNumber(transferDTO.accountOrigin());
            Account destination = accountRepository.findByNumber(transferDTO.accountDestination());

            if (!clientOrigin.getAccounts().stream().anyMatch(a -> a.getNumber().equals(transferDTO.accountOrigin()))) {
                response.put("error", false);
                response.put("message", "account not found");
                return response;
            }

            if (transferDTO.accountOrigin().equals(transferDTO.accountDestination())) {
                response.put("error", false);
                response.put("message", "select another account");
                return response;
            }

            if (!accountRepository.existsByNumber(transferDTO.accountDestination())) {
                response.put("error", false);
                response.put("message", "account not found");
                return response;
            }

            if (transferDTO.amount() <= 0) {
                response.put("error", false);
                response.put("message", "transfer amount must be positive");
                return response;
            }

            if (!origin.getClient().equals(clientOrigin)) {
                response.put("error", false);
                response.put("message", "unauthorized transfer");
                return response;
            }

            if (origin.getBalance() < transferDTO.amount()) {
                response.put("error", false);
                response.put("message", "insufficient funds");
                return response;
            }

            origin.setBalance(origin.getBalance() - transferDTO.amount());
            destination.setBalance(destination.getBalance() + transferDTO.amount());

            accountRepository.save(origin);
            accountRepository.save(destination);

            Transaction transactionOrigin = new Transaction(TransactionType.DEBIT, "transfer to " +transferDTO.accountDestination(), LocalDateTime.now(), -transferDTO.amount());
            transactionOrigin.setAccount(origin);
            transactionOrigin.setClient(clientOrigin);
            transactionRepository.save(transactionOrigin);

            Transaction transactionDestination = new Transaction(TransactionType.CREDIT, "transfer from " +transferDTO.accountOrigin(), LocalDateTime.now(), transferDTO.amount());
            transactionDestination.setAccount(destination);
            transactionDestination.setClient(clientDestination);
            transactionRepository.save(transactionDestination);

            response.put("success", true);
            response.put("message", "Successfully transferred");
            return response;
        } catch (Exception e) {
            response.put("error", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return response;
        }
    }
}