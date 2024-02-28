package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardFormDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utilitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients.stream().map(ClientDTO::new).collect(Collectors.toList()), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ClientDTO clientDTO = new ClientDTO(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Hello " + mail);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        return ResponseEntity.ok(new ClientDTO(client));
    }

    @PostMapping("/current/account")
    public ResponseEntity<?> createAccount(){

        String accountNumber = RandomUtil.generateAccountNumber(8);
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Already has 3 accounts", HttpStatus.BAD_REQUEST);
        }

        while (accountRepository.existsByNumber(accountNumber)) {
            accountNumber = RandomUtil.generateAccountNumber(8);
        }

        Account newAccount = new Account(accountNumber,LocalDate.now(), 0.0);
        newAccount.setClient(client);
        accountRepository.save(newAccount);

        return ResponseEntity.ok("Account created successfully!");
    }


    @GetMapping("/current/accounts")
    public ResponseEntity<?> getAccounts(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        return ResponseEntity.ok(client.getAccounts().stream().map(AccountsDTO::new).collect(Collectors.toList()));
    }



    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CardFormDTO cardForm){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userMail);
        Long cardNumber = RandomUtil.generateNumber(16);
        int cardCVV = RandomUtil.generateNumber(3).intValue();

        List<Card> clientCards = client.getCard();
        if (clientCards == null || clientCards.isEmpty()) {
            clientCards = new ArrayList<>();
        }

        if (clientCards.size() >= 3 ) {
            return new ResponseEntity<>("You already have 3 cards", HttpStatus.BAD_REQUEST);
        }

        if (clientCards.stream().anyMatch(card -> card.getType().equals(CardType.valueOf(cardForm.cardType())))) {
            return new ResponseEntity<>("You already have a card of this type", HttpStatus.BAD_REQUEST);
        }

        if (clientCards.stream().anyMatch(card -> card.getColor().equals(CardColor.valueOf(cardForm.cardColor())))) {
            return new ResponseEntity<>("You already have a card of this color", HttpStatus.BAD_REQUEST);
        }

        Card newCard = new Card(CardType.valueOf(cardForm.cardType()), CardColor.valueOf(cardForm.cardColor()), cardNumber, cardCVV, LocalDate.now(), LocalDate.now().plusYears(5));

        newCard.setClient(client);
        clientCards.add(newCard);
        client.setCard(clientCards);

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
