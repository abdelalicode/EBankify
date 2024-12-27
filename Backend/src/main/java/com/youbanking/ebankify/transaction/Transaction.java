package com.youbanking.ebankify.transaction;

import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.user.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_account_id", nullable = false)
    private BankAccount senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private BankAccount receiverAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;


    @Column(nullable = false, updatable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    private LocalDateTime expectedTime;

    private Double transactionFee;

    private Integer recurrenceDays;

    private LocalDateTime recurrenceEndDate;

    @ManyToOne
    @JoinColumn(name = "approved_by_employee_id")
    private User approvedBy;

}


