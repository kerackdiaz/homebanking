package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loan")
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/types")
    public ResponseEntity<List<LoanDTO>> getAllLoanTypes() {
        List<Loan> loans = loanRepository.findAll();
        List<LoanDTO> loanDTOs = loans.stream().map(LoanDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }
}
