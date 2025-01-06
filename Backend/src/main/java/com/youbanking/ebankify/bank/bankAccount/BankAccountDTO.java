package com.youbanking.ebankify.bank.bankAccount;


import org.springframework.data.annotation.Id;

public record BankAccountDTO(
        @Id
        Long id,
        String accountNumber,
        String bankAccountType,
        java.math.BigDecimal balance,
        String ClientLastName,
        String ClientFirstName,

        boolean isActive
) {

}
