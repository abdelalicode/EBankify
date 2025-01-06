package com.youbanking.ebankify.exception;

import com.youbanking.ebankify.bank.bankAccount.BankAccountTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.FOUND)
public class BankAccountAlreadyFoundException extends RuntimeException {

    public BankAccountAlreadyFoundException(BankAccountTypes accountType) {
        super("You already have a " + accountType + " account. Available types are: " +
                Arrays.stream(BankAccountTypes.values())
                        .filter(type -> type != accountType)
                        .map(Enum::name)
                        .collect(Collectors.joining(", ")) + ".");
    }
}
