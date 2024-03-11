package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, LoanRepository loanRepository,TransactionRepository transactionRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return  args -> {
//			Client Melba = new Client("Melba", "Morel", "melba@mindhub.com", "123456");
//			Account melba1 = new Account("VIN-00000001", LocalDate.now(), 5000.00);
//			Account melba2 = new Account("VIN-00000002", LocalDate.now(), 7500.00);
//
//			Loan mortgage = new Loan("Mortgage", 500000.0, Arrays.asList(12, 24, 36, 48, 60));
//			Loan personal = new Loan("Personal", 100000.0, Arrays.asList(6, 12, 24));
//			Loan automotive = new Loan("Automotive", 300000.0, Arrays.asList(6, 12, 24, 36));
//
//			ClientLoan mortgage1 = new ClientLoan(400000,60 );
//			ClientLoan personal1 = new ClientLoan(50000,12 );
//			Card melbaCard1 = new Card(CardType.DEBIT, CardColor.GOLD, 3325674578764445L, 990, LocalDate.now(), LocalDate.now().plusYears(5));
//
//
//
//			Transaction transaction1 = new Transaction(TransactionType.DEBIT, "NonProfit LiveStream", LocalDateTime.now(), -30021);
//			Transaction transaction2 = new Transaction(TransactionType.DEBIT, "Sockets Buy",LocalDateTime.now(), -300);
//			Transaction transaction3 = new Transaction(TransactionType.CREDIT, "Bet win",LocalDateTime.now(), 26434);
//			Transaction transaction4 = new Transaction(TransactionType.CREDIT, "Donation",LocalDateTime.now(), 3671);
//
//
//
//			melbaCard1.setClient(Melba);
//			melbaCard1.setCardHolder(Melba);
//
//			Melba.addAccount(melba1);
//			Melba.addAccount(melba2);
//			mortgage1.setClient(Melba);
//			personal1.setClient(Melba);
//			mortgage1.setLoan(mortgage);
//			personal1.setLoan(personal);
//			transaction1.setClient(Melba);
//			transaction2.setClient(Melba);
//			transaction3.setClient(Melba);
//			transaction4.setClient(Melba);
//			transaction1.setAccount(melba1);
//			transaction2.setAccount(melba2);
//			transaction3.setAccount(melba1);
//			transaction4.setAccount(melba2);
//
//			clientRepository.save(Melba);
//			accountRepository.save(melba1);
//			accountRepository.save(melba2);
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//			loanRepository.save(mortgage);
//			loanRepository.save(personal);
//			loanRepository.save(automotive);
//			clientLoanRepository.save(mortgage1);
//			clientLoanRepository.save(personal1);
//			cardRepository.save(melbaCard1);

			System.out.println("\033[0;34m" + "initialized application" + "\033[0m");
		};
	}


}
