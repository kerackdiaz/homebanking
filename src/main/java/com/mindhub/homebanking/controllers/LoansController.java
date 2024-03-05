package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.LoanRequestDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loan")
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository ClientRepository;

    @GetMapping("/types")
    public ResponseEntity<List<LoanDTO>> getAllLoanTypes() {
        List<Loan> loans = loanRepository.findAll();
        List<LoanDTO> loanDTOs = loans.stream().map(LoanDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }


    @Transactional
    @PostMapping("/request")
    public ResponseEntity<?> createLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        try {
            Loan loanName = loanRepository.findByName(loanRequestDTO.name());
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Client client = ClientRepository.findByEmail(userEmail);
            Account destinationAccount = accountRepository.findByNumber(loanRequestDTO.accountDestination());
            double totalAmount = loanRequestDTO.amount() * 1.20;

            if (loanRequestDTO.amount() <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
            if (loanRequestDTO.payments() <= 0) {
                throw new IllegalArgumentException("You have to choose a quantity of payments");
            }
            if (loanName == null) {
                return new ResponseEntity<>("The loan does not exist", HttpStatus.NOT_FOUND);
            }
            if (loanRequestDTO.amount() > loanName.getMaxAmount()) {
                return new ResponseEntity<>("The amount is not available", HttpStatus.NOT_FOUND);
            }
            if (!loanName.getPayments().contains(loanRequestDTO.payments())) {
                return new ResponseEntity<>("The quantity of payments is not available", HttpStatus.NOT_FOUND);
            }
            if (destinationAccount == null) {
                return new ResponseEntity<>("The account does not exist", HttpStatus.NOT_FOUND);
            }
            if (!destinationAccount.getClient().equals(client)) {
                return new ResponseEntity<>("The account does not belong to the client", HttpStatus.FORBIDDEN);
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
            ClientRepository.save(client);


            return new ResponseEntity<>(new LoanDTO(newLoan), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
