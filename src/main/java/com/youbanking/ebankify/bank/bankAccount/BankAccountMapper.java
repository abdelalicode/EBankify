package com.youbanking.ebankify.bank.bankAccount;

import com.youbanking.ebankify.user.User;

public class BankAccountMapper {

    public static BankAccountDTO toDTO(BankAccount entity) {
        return new BankAccountDTO(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getBankAccountType().toString(),
                entity.getBalance(),
                entity.getClient().getLastName(),
                entity.getClient().getFirstName(),
                entity.isActive()

        );
    }

    public static BankAccountResponseDTO toResponseDTO(BankAccount entity) {
        return new BankAccountResponseDTO(
                entity.getAccountNumber(),
                entity.getBankAccountType(),
                entity.getClient().getFirstName(),
                entity.getClient().getLastName(),
                entity.getBalance(),
                entity.isActive()
        );
    }

    public static BankAccount toEntity(BankAccountDTO dto) {
        BankAccount entity = new BankAccount();
        entity.setId(dto.id());
        entity.setAccountNumber(dto.accountNumber());
        entity.setBankAccountType(BankAccountTypes.valueOf(dto.bankAccountType()));
        entity.setBalance(dto.balance());
        entity.setActive(dto.isActive());
        return entity;
    }
}

