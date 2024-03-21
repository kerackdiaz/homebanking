package com.mindhub.homebanking.dtos;

public record TransferDTO(String accountOrigin, String accountDestination, double amount, String description) {
//    public TransferDTO {
//        if (accountOrigin == null || accountOrigin.isBlank()) {
//            throw new IllegalArgumentException("Account origin is required");
//        }
//        if (accountDestination == null || accountDestination.isBlank()) {
//            throw new IllegalArgumentException("Account destination is required");
//        }
//
//        if (description == null || description.isBlank()) {
//            throw new IllegalArgumentException("Description is required");
//        }
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than 0");
//        }
//    }

}
