package com.youbanking.ebankify.asset;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssetDTO(AssetType assetType, BigDecimal estimatedValue, LocalDateTime acquisitionDate) {

}
