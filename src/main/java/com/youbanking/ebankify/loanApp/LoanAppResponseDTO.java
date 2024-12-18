package com.youbanking.ebankify.loanApp;


import com.youbanking.ebankify.asset.Asset;
import com.youbanking.ebankify.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoanAppResponseDTO {

    private int id;

    private BigDecimal amount;

    private LoanType loanType;

    private User client;

    private List<Asset> clientAssets;

    private LocalDateTime createdAt;


}
