package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardFormDTO;
import com.mindhub.homebanking.servicies.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CardFormDTO cardForm){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cardService.createCard(cardForm, userMail), HttpStatus.OK);
    }

    @GetMapping("/current/cards")
    public ResponseEntity<?> getCards(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cardService.getCards(userMail).stream().map(CardDTO::new).collect(Collectors.toList()));
    }


    @DeleteMapping("/current/cards/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cardService.deleteCard(id, userMail), HttpStatus.OK);
    }
}
