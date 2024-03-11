package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.dtos.LoanRequestDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Loan createLoan(LoanRequestDTO loanRequestDTO, String userMail) throws Exception {
        try {
            Loan loanName = loanRepository.findByName(loanRequestDTO.name());
            Client client = clientRepository.findByEmail(userMail);
            Account destinationAccount = accountRepository.findByNumber(loanRequestDTO.accountDestination());
            double totalAmount = loanRequestDTO.amount() * 1.20;
            if (loanRequestDTO.amount() <= 0 || loanRequestDTO.payments() <= 0) {
                return null;
            }
            if (loanName == null || loanRequestDTO.amount() > loanName.getMaxAmount() || !loanName.getPayments().contains(loanRequestDTO.payments())) {
                return null;
            }
            if (destinationAccount == null || !destinationAccount.getClient().equals(client)) {
                return null;
            }

            Loan newLoan = new Loan(loanRequestDTO.name(), totalAmount, List.of(loanRequestDTO.payments()));
            loanRepository.save(newLoan);

            ClientLoan clientLoan = new ClientLoan(loanRequestDTO.amount(), loanRequestDTO.payments());
            clientLoan.setClient(client);
            clientLoan.setLoan(newLoan);
            clientLoanRepository.save(clientLoan);

            Transaction transaction1 = new Transaction(TransactionType.CREDIT, "Loan " + loanRequestDTO.name() + " approved", LocalDateTime.now(), totalAmount);
            transactionRepository.save(transaction1);
            destinationAccount.addTransaction(transaction1);
            destinationAccount.setBalance(destinationAccount.getBalance() + totalAmount);

            accountRepository.save(destinationAccount);
            client.addAccount(destinationAccount);
            clientRepository.save(client);

            return newLoan;
        } catch (Exception e) {
            throw new Exception("An error occurred: " + e.getMessage());
        }
    }
}