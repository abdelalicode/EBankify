package com.youbanking.ebankify.bank.HQ;

import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Bank {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String BIC;
        private String name;
        private BigDecimal basicLimit;

        @OneToMany(mappedBy = "bank")
        private List<BankAccount> bankaccounts;


}
