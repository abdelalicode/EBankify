package com.youbanking.ebankify.bank.bankAccount;

import com.youbanking.ebankify.bank.HQ.BankRepository;
import com.youbanking.ebankify.exception.BankAccountAlreadyFoundException;
import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private final BankRepository bankRepository;
    private BankAccountRepository repository;
    private UserRepository userRepository;




    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository, BankRepository bankRepository) {
        this.repository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    public List<BankAccountDTO> getAllBankAccounts() {
        return repository.findAll().stream()
                .map(BankAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BankAccountResponseDTO createAccount(BankAccountDTO dto, Long userId) {


        BankAccount entity = BankAccountMapper.toEntity(dto);
        User user = userRepository.findById(userId).orElse(null);

        Optional<BankAccount> existingCurrentAccount = repository.findTypeAccountByUserId(userId, entity.getBankAccountType());

        if (existingCurrentAccount.isPresent()) {
            throw new BankAccountAlreadyFoundException(entity.getBankAccountType());

        }

        entity.setClient(user);
        entity.setBalance(BigDecimal.ZERO);
        entity.setActive(true);
        entity.setAccountNumber(generateAccountNumber());
        entity.setBank(bankRepository.findByBIC("CIHMMAMC"));
        entity.setBankAccountType(entity.getBankAccountType());

        BankAccount savedEntity = repository.save(entity);
        return BankAccountMapper.toResponseDTO(savedEntity);
    }

    public Optional<BankAccount> getBankAccount(Long id) {
        return repository.findById(id);
    }

    public BankAccount update(BankAccount bankAccount, Long id) {
        BankAccount entity = repository.findById(id).orElse(null);
        if(entity != null) {
            entity.setBankAccountType(bankAccount.getBankAccountType());

            entity.setActive(bankAccount.isActive());
            return repository.save(entity);
        }
        return null;
    }


    private static String generateAccountNumber() {
        String prefix = "DET";
        Random random = new Random();
        int randomNumber = 100000000 + random.nextInt(900000000);

        return prefix + randomNumber;
    }






}
