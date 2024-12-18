package com.youbanking.ebankify.loanApp;


import com.youbanking.ebankify.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoanApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approvedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

}
