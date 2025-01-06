package com.youbanking.ebankify.loanApp;

import java.math.BigDecimal;

public record LoanAppDTO(BigDecimal amount, LoanType loanType) {
}
