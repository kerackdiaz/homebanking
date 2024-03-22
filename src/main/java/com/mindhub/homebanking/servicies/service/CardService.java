package com.mindhub.homebanking.servicies.service;

import com.mindhub.homebanking.dtos.CardFormDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utilitis.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CardService {


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Map<String, Object> createCard(CardFormDTO cardForm, String userMail) {
        Map<String, Object> response = new HashMap<>();
        Client client = clientRepository.findByEmail(userMail);
        long cardNumber = RandomUtil.generateNumber(16);
        long cardCVV = RandomUtil.generateNumber(3);
        Long cardCount = cardRepository.countByClientId(client.getId());
        if (cardCount >= 3) {
            response.put("success", false);
            response.put("message", "You already have 3 cards");
            return response;
        }

        if (client.getCard().stream().anyMatch(card -> card.getType().equals(CardType.valueOf(cardForm.cardType())))) {
            response.put("success", false);
            response.put("message", "You already have a card of this type");
            return response;
        }

        if (client.getCard().stream().anyMatch(card -> card.getColor().equals(CardColor.valueOf(cardForm.cardColor())))) {
            response.put("success", false);
            response.put("message", "You already have a card of this color");
            return response;
        }

        Card newCard = new Card(CardType.valueOf(cardForm.cardType()), CardColor.valueOf(cardForm.cardColor()), cardNumber, (int) cardCVV, LocalDate.now(), LocalDate.now().plusYears(5));

        newCard.setClient(client);
        newCard.setCardHolder(client);

        cardRepository.save(newCard);
        clientRepository.save(client);

        response.put("success", true);
        response.put("message", "Card created successfully");
        return response;
    }


    public List<Card> getCards(String userMail) {
        Client client = clientRepository.findByEmail(userMail);
        if (client == null) {
            return null;
        }
        return client.getCard();
    }

    @Transactional
    public Map<String, Object> deleteCard(Long id, String userMail) {
        Client client = clientRepository.findByEmail(userMail);
        if (client.getCard().stream().anyMatch(card -> card.getId() == id)) {
            Card card = cardRepository.findById(id).get();
            cardRepository.delete(card);

            clientRepository.save(client);
            return Map.of("success", true, "message", "Card deleted successfully");
        }

        return Map.of("success", false, "message", "Card not found");
    }
}