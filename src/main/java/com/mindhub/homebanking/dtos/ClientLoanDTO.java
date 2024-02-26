package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private  long id;
    private long ClientId;
    private long loanId;
    private String loanName;
    private double Amount;
    private double Payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.ClientId = clientLoan.getClient().getId();
        this.loanId = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.Amount = clientLoan.getAmount();
        this.Payments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public long getClientId() {
        return ClientId;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getAmount() {
        return Amount;
    }

    public double getPayments() {
        return Payments;
    }
}
