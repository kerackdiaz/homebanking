package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardFormDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utilitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {


    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;


    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CardFormDTO cardForm){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        Long cardNumber = RandomUtil.generateNumber(16);
        int cardCVV = RandomUtil.generateNumber(3).intValue();
        Long cardCount = cardRepository.countByClientId(client.getId());


        if (cardCount >= 3 ) {
            return new ResponseEntity<>("You already have 3 cards", HttpStatus.BAD_REQUEST);
        }

        if (cardRepository.existsByType(CardType.valueOf(cardForm.cardType()))) {
            return new ResponseEntity<>("You already have a card of this type", HttpStatus.BAD_REQUEST);
        }

        if (cardRepository.existsByColor(CardColor.valueOf(cardForm.cardColor()))) {
            return new ResponseEntity<>("You already have a card of this color", HttpStatus.BAD_REQUEST);
        }

        Card newCard = new Card(CardType.valueOf(cardForm.cardType()), CardColor.valueOf(cardForm.cardColor()), cardNumber, cardCVV, LocalDate.now(), LocalDate.now().plusYears(5));

        newCard.setClient(client);
        newCard.setCardHolder(client);

        cardRepository.save(newCard);
        clientRepository.save(client);

        return ResponseEntity.ok("success");
    }


    @GetMapping("/current/cards")
    public ResponseEntity<?> getCards(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        return ResponseEntity.ok(client.getCard().stream().map(CardDTO::new).collect(Collectors.toList()));
    }
}
