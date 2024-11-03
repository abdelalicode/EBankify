package com.youbanking.ebankify.bank.bankAccount;

public class BankAccountMapper {

    public static BankAccountDTO toDTO(BankAccount entity) {
        return new BankAccountDTO(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getBalance(),
                entity.isActive()
        );
    }

    public static BankAccount toEntity(BankAccountDTO dto) {
        BankAccount entity = new BankAccount();
        entity.setId(dto.id());
        entity.setAccountNumber(dto.accountNumber());
        entity.setBalance(dto.balance());
        entity.setActive(dto.isActive());
        return entity;
    }
}

