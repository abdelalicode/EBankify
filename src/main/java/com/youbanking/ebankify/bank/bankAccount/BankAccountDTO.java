package com.youbanking.ebankify.bank.bankAccount;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.Id;

public record BankAccountDTO(
        @Id
        Long id,
        @NotEmpty
        String accountNumber,
        @PositiveOrZero
        Double balance,
        boolean isActive
) {

}
