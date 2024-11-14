package com.youbanking.ebankify.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransApproveRequestDTO {

    private String transactionId;
    private Boolean approved;

}
