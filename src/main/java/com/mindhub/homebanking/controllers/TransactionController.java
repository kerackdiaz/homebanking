package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransferDTO;

import com.mindhub.homebanking.servicies.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Transactional
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDTO transferDTO) {
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> result = transactionService.transfer(transferDTO, userMail);
        if (result.get("success").equals(true)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
