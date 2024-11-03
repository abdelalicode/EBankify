package com.youbanking.ebankify.bank.bankAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class BankAccountJsonLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BankAccountJsonLoader.class);
    private final BankAccountRepository bankAccountRepository;
    private final ObjectMapper objectMapper;

    public BankAccountJsonLoader(BankAccountRepository bankAccountRepository, ObjectMapper objectMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
            if(bankAccountRepository.count() == 0 ) {
                try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/bankAccounts.json")) {
                    BanAccounts bankAccounts = objectMapper.readValue(inputStream, BanAccounts.class);
                    bankAccountRepository.saveAll(bankAccounts.bankaccounts());
                }
                catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }

            }
            else {
                log.info("Found {} bankAccounts", bankAccountRepository.count());
            }
    }
}
