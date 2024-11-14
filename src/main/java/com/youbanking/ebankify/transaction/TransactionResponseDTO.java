package com.youbanking.ebankify.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO
        (UUID id,
         TransactionStatus transactionStatus,
         String senderName,
         String receiverName,
         String receiverBankName,
         LocalDateTime transactionDate,
         Double transactionFee  ) {
}
