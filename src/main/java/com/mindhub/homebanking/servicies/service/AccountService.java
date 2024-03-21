package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utilitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Map<String, Object> createAccount(String userMail) {
        Map<String, Object> response = new HashMap<>();
        String accountNumber = RandomUtil.generateAccountNumber(8);
        Client client = clientRepository.findByEmail(userMail);

        if (client.getAccounts().size() >= 3) {
            response.put("error", true);
            response.put("message", "Already has 3 accounts");
            return response;
        }

        while (accountRepository.existsByNumber(accountNumber)) {
            accountNumber = RandomUtil.generateAccountNumber(8);
        }

        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0);
        newAccount.setClient(client);
        accountRepository.save(newAccount);

        response.put("success", true);
        response.put("message", "Account created successfully!");
        return response;
    }

    public List<Account> getAccounts(String userMail) {
        Client client = clientRepository.findByEmail(userMail);
        return client.getAccounts();
    }
}