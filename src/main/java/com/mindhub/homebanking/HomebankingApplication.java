package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return  args -> {
			Client Melba = new Client("Melba", "Morel", "melba@mindhub.com");
			Account melba1 = new Account("123456", LocalDate.now(), 5000.00);
			Account melba2 = new Account("78945", LocalDate.now(), 7500.00);

			Loan mortgage = new Loan("Mortgage", 500000.0, Arrays.asList(12, 24, 36, 48, 60));
			Loan personal = new Loan("Personal", 100000.0, Arrays.asList(6, 12, 24));
			Loan automotive = new Loan("Automotive", 300000.0, Arrays.asList(6, 12, 24, 36));

			ClientLoan mortgage1 = new ClientLoan(400000,60 );
			ClientLoan personal1 = new ClientLoan(50000,12 );
			Card melbaCard1 = new Card(CardType.DEBIT, CardColor.GOLD, 3325674578764445L, 990, LocalDate.now(), LocalDate.now().plusYears(5));





			melbaCard1.setClient(Melba);
			melbaCard1.setCardHolder(Melba);
			Melba.addAccount(melba1);
			Melba.addAccount(melba2);
			mortgage1.setClient(Melba);
			personal1.setClient(Melba);
			mortgage1.setLoan(mortgage);
			personal1.setLoan(personal);
			clientRepository.save(Melba);
			accountRepository.save(melba1);
			accountRepository.save(melba2);
			loanRepository.save(mortgage);
			loanRepository.save(personal);
			loanRepository.save(automotive);
			clientLoanRepository.save(mortgage1);
			clientLoanRepository.save(personal1);
			cardRepository.save(melbaCard1);
			System.out.println(Melba);
		};
	}


}
