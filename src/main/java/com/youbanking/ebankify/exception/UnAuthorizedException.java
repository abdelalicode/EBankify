package com.youbanking.ebankify.exception;

import com.youbanking.ebankify.bank.bankAccount.BankAccountTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

        public UnAuthorizedException(String message) {
            super(message);
        }



}
