package com.youbanking.ebankify.exception;

import com.youbanking.ebankify.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccountAlreadyFoundException.class)
    public ResponseEntity<Object> handleBankAccountAlreadyFoundException(BankAccountAlreadyFoundException ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                "BANK_ACCOUNT_ALREADY_EXISTS"
        );
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                "401"
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                "401"
        );
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Object> UnAuthorizedException(UnAuthorizedException ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                "401"
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                "404"
        );
    }

    @ExceptionHandler(NotEligibleException.class)
    public ResponseEntity<Object> handleNotEligibleException(NotEligibleException ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.FORBIDDEN,
                "403"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return ResponseHandler.errorBuilder(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR"
        );
    }
}
