package com.youbanking.ebankify.bank.HQ;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BankNotFoundException extends RuntimeException {

    public BankNotFoundException() {
        super("Account not found");
    }
}
