package com.mindhub.homebanking.dtos;

public record RegisterDTO(String firstName, String lastName, String email, String password) {

    public RegisterDTO {
        if (firstName.isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}


