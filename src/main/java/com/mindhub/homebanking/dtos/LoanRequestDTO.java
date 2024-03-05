package com.mindhub.homebanking.dtos;

public record LoanRequestDTO(String name, String accountDestination, double amount, int payments) {
}
