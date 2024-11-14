package com.youbanking.ebankify.transaction;

import org.mapstruct.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receiverAccount.accountNumber", source = "accountNumber")
    @Mapping(target = "senderAccount", ignore = true)
    @Mapping(target = "transactionStatus", constant = "PENDING")
    @Mapping(target = "transactionDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "transactionFee", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "recurrenceDays", source = "recurringDetails.frequency")
    @Mapping(target = "recurrenceEndDate", source = "recurringDetails.endDate")
    Transaction toTransaction(TransactionRequestDTO requestDTO);


    @Mapping(target = "id" , source = "transactionId")
    @Mapping(target = "transactionStatus" , source = "approved")
    Transaction toTransaction(TransApproveRequestDTO transaction);

    @AfterMapping
    default void setRecurrenceDetails(TransactionRequestDTO requestDTO, @MappingTarget Transaction transaction) {
        if (requestDTO.getRecurringDetails() != null && !requestDTO.getRecurringDetails().isRecurring()) {
            transaction.setRecurrenceDays(null);
            transaction.setRecurrenceEndDate(null);
        }
    }

    default TransactionStatus map(Boolean approved) {
        return approved ? TransactionStatus.APPROVED : TransactionStatus.REJECTED;
    }

    @InheritInverseConfiguration
    @Mapping(target = "senderName" , expression = "java(transaction.getSenderAccount().getClient().getFirstName() + ' ' + transaction.getSenderAccount().getClient().getLastName())")
    @Mapping(target = "receiverName" , expression = "java(transaction.getReceiverAccount().getClient().getFirstName() + ' ' + transaction.getReceiverAccount().getClient().getLastName())")
    @Mapping(target = "receiverBankName" , source = "transaction.receiverAccount.bank.name")
    TransactionResponseDTO toDTO(Transaction transaction);


    default List<TransactionResponseDTO> toDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}