package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Boolean existsByType(CardType Type);
    Boolean existsByColor(CardColor color);
    Long countByClientId(Long clientId);
    

    Object findByClientId(long id);
}
