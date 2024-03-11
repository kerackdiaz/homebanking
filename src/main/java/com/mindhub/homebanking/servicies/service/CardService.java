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
        Long cardNumber = RandomUtil.generateNumber(16);
        int cardCVV = RandomUtil.generateNumber(3).intValue();
        Long cardCount = cardRepository.countByClientId(client.getId());

        if (cardCount >= 3 ) {
            response.put("success", false);
            response.put("message", "You already have 3 cards");
            return response;
        }

        if (cardRepository.existsByType(CardType.valueOf(cardForm.cardType()))) {
            response.put("success", false);
            response.put("message", "You already have a card of this type");
            return response;
        }

        if (cardRepository.existsByColor(CardColor.valueOf(cardForm.cardColor()))) {
            response.put("success", false);
            response.put("message", "You already have a card of this color");
            return response;
        }

        Card newCard = new Card(CardType.valueOf(cardForm.cardType()), CardColor.valueOf(cardForm.cardColor()), cardNumber, cardCVV, LocalDate.now(), LocalDate.now().plusYears(5));

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
        return client.getCard();
    }
}