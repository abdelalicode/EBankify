package com.youbanking.ebankify.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnsufficientFundException extends RuntimeException {

    public UnsufficientFundException(String message) {
        super(message);
    }
}
