package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.LoanRequestDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.servicies.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loan")
public class LoansController {


    @Autowired
    private LoanService loanService;

    @GetMapping("/types")
    public ResponseEntity<List<LoanDTO>> getAllLoanTypes() {
        return new ResponseEntity<>(loanService.getAllLoanTypes().stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/request")
    public ResponseEntity<?> createLoan(@RequestBody LoanRequestDTO loanRequestDTO) throws Exception {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Loan newLoan = loanService.createLoan(loanRequestDTO, userMail);
        if (newLoan == null) {
            return new ResponseEntity<>("Error creating loan", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new LoanDTO(newLoan), HttpStatus.CREATED);
    }
}

