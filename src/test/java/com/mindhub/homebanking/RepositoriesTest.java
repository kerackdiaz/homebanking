package com.mindhub.homebanking;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void testFindAll() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, not(empty()));
    }

    @Test
    public void testCardCount() {
        Long cardCount = cardRepository.countByClientId(1L);
        assertTrue(cardCount > 0, "Client with ID 1 should have at least one card.");
    }

    @Test
    public void testClientHasMaxThreeCards() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            assertFalse(client.getCard().size() > 3, "Client " + client.getFirstName() + " can't have more than 3 cards.");
        }
    }

    @Test
    public void testClientHasMaxOneBlackCard() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            int goldCards = 0;
            for (Card card : client.getCard()) {
                if (card.getType().equals("GOLD")) {
                    goldCards++;
                }
            }
            assertFalse(goldCards > 1, "Client " + client.getFirstName() + " can't have more than 1 black card.");
        }
    }

    @Test
    public void testAccountGetByNumber() {
        Account account = accountRepository.findByNumber("VIN-00000001");
        System.out.println(account);
        assertEquals("VIN-00000001", account.getNumber());
    }

    @Test
    public void testAccountExistsByNumber() {
        assertTrue(accountRepository.existsByNumber("VIN-00000001"));
    }


}