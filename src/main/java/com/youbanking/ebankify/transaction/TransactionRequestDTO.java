package com.youbanking.ebankify.transaction;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionRequestDTO {

    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    private TransactionType transactionType;

    private RecurringDetails recurringDetails;


    @Setter
    @Getter
    public static class RecurringDetails {
        private boolean isRecurring;

        private Integer frequency;

        private LocalDateTime endDate;

    }



}
