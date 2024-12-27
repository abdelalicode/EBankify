package com.youbanking.ebankify.bank.bankAccount;

import com.youbanking.ebankify.bank.HQ.Bank;
import com.youbanking.ebankify.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "bank_account")
public class BankAccount {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String accountNumber;

        @Enumerated(EnumType.STRING)
        private BankAccountTypes bankAccountType;

        private BigDecimal balance;

        private boolean isActive;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "client_id")
        private User client;

        @ManyToOne
        @JoinColumn(name = "bank_id")
        private Bank bank;

        @Override
        public String toString() {
                return " AccountType='" + bankAccountType  +
                        ", Active=" + isActive ;
        }

}
