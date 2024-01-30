package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository ){
		return  args -> {
			Client Melba = new Client("Melba", "Morel", "melba@mindhub.com");
			Account melba1 = new Account("123456", LocalDate.now(), 5000.00);
			Account melba2 = new Account("78945", LocalDate.now(), 7500.00);

			clientRepository.save(Melba);
			Melba.addAccount(melba1);
//
			Melba.addAccount(melba2);
			accountRepository.save(melba1);
			accountRepository.save(melba2);
			System.out.println(Melba);
		};
	}

}
