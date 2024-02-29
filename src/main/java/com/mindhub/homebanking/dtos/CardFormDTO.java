package com.mindhub.homebanking.dtos;

public record CardFormDTO(String cardColor, String cardType) {

    public CardFormDTO {
        if (cardColor.isBlank() && cardType.isBlank() ) {
            throw new IllegalArgumentException("All fields are required");
        }
    }
}
