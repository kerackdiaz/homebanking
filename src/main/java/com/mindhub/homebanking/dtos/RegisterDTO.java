package com.mindhub.homebanking.dtos;

public record RegisterDTO(String firstName, String lastName, String email, String password) {
    public RegisterDTO {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("All fields are required");
        }
    }

}
