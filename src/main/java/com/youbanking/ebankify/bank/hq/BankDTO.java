package com.youbanking.ebankify.bank.HQ;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record BankDTO(
        @Id
        Long id,
        @PositiveOrZero
        String BIC,
        String name,
        BigDecimal basicLimit
) {

}
