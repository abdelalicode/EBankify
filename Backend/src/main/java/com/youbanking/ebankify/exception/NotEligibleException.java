package com.youbanking.ebankify.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEligibleException extends RuntimeException {

    public NotEligibleException(String message) {
        super(message);
    }
}
