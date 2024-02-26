package com.mindhub.homebanking.dtos;

public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}
