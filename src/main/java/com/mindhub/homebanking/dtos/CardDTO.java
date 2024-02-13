package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardDTO {

    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private Long number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        String numberStr = String.valueOf(number);
        return numberStr.replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3-$4");
    }

    public int getCvv() {
        return cvv;
    }

    public String getFromDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return fromDate.format(formatter);
    }

    public String getThruDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return thruDate.format(formatter);
    }
}
