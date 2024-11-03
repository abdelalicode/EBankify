package com.youbanking.ebankify.bank.bankAccount;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private BankAccountRepository repository;


    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.repository = bankAccountRepository;
    }

    public List<BankAccountDTO> getAllBankAccounts() {
        return repository.findAll().stream()
                .map(BankAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BankAccountDTO createAccount(BankAccountDTO dto) {
        BankAccount entity = BankAccountMapper.toEntity(dto);
        BankAccount savedEntity = repository.save(entity);
        return BankAccountMapper.toDTO(savedEntity);
    }

    public Optional<BankAccountDTO> getBankAccount(Long id) {
        return repository.findById(id).map(BankAccountMapper::toDTO);
    }






}
