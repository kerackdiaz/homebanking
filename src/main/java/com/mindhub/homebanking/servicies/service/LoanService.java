package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.dtos.LoanRequestDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Loan> getAllLoanTypes() {
        return loanRepository.findAll();
    }

    public Map<String, Object> createLoan(LoanRequestDTO loanRequestDTO, String userMail) {
        Map<String, Object> response = new HashMap<>();
        try {
            Loan loanName = loanRepository.findByName(loanRequestDTO.name());
            Client client = clientRepository.findByEmail(userMail);
            Account destinationAccount = accountRepository.findByNumber(loanRequestDTO.accountDestination());
            double totalAmount = loanRequestDTO.amount() * 1.20;


            if (loanRequestDTO.amount() <= 0 || loanRequestDTO.payments() <= 0) {
                response.put("error", false);
                response.put("message", "Amount and payments must be greater than 0");
                return response;
            }

            if (loanName == null || loanRequestDTO.amount() > loanName.getMaxAmount() || !loanName.getPayments().contains(loanRequestDTO.payments())) {
                response.put("error", false);
                response.put("message", "Invalid loan request");
                return response;
            }

            if (destinationAccount == null || !destinationAccount.getClient().equals(client)) {
                response.put("error", false);
                response.put("message", "Invalid destination account");
                return response;
            }


            ClientLoan newLoan = new ClientLoan(totalAmount, loanRequestDTO.payments());
            newLoan.setClient(client);
            newLoan.setLoan(loanName);
            clientLoanRepository.save(newLoan);

            Transaction transaction1 = new Transaction(TransactionType.CREDIT, "Loan " + loanRequestDTO.name() + " approved", LocalDateTime.now(), loanRequestDTO.amount());
            transaction1.setAccount(destinationAccount);
            transaction1.setClient(client);
            transactionRepository.save(transaction1);

            destinationAccount.setBalance(destinationAccount.getBalance() + loanRequestDTO.amount());
            accountRepository.save(destinationAccount);

            response.put("success", true);
            response.put("message", "Loan successfully created");
            return response;
        } catch (Exception e) {
            response.put("error", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return response;
        }
    }
}