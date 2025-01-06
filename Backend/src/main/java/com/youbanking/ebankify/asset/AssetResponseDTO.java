package com.youbanking.ebankify.asset;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDTO {

    private int id;

    private String ownerFirstName;
    private String ownerLastName;

    private String assetType;

    private BigDecimal estimatedValue;

    private LocalDateTime acquisitionDate;


}
