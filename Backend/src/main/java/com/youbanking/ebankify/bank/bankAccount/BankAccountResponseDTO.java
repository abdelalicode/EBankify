package com.youbanking.ebankify.bank.bankAccount;


public record BankAccountResponseDTO(
        String accountNumber,
        BankAccountTypes bankAccountType,
        String clientFirstName,
        String clientLastName,
        java.math.BigDecimal balance,
        boolean isActive
) {

}
