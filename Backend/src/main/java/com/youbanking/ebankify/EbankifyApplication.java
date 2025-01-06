package com.youbanking.ebankify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbankifyApplication {

	private static final Logger log = LoggerFactory.getLogger(EbankifyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EbankifyApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(BankAccountRepository bankAccountRepository) {
//		return args -> {
//			BankAccount bankAccount = new BankAccount(1L, "ZE3434", 3000.00, true);
//            bankAccountRepository.create(bankAccount);
//		};
//	}

}
