package com.youbanking.ebankify.asset;

import com.youbanking.ebankify.bank.bankAccount.BankAccount;
import com.youbanking.ebankify.bank.bankAccount.BankAccountDTO;
import com.youbanking.ebankify.bank.bankAccount.BankAccountResponseDTO;
import com.youbanking.ebankify.bank.bankAccount.BankAccountTypes;

public class AssetMapper {


    public static AssetResponseDTO toResponseDTO(Asset entity) {
        return new AssetResponseDTO(
                entity.getId(),
                entity.getOwner().getFirstName(),
                entity.getOwner().getLastName(),
                entity.getAssetType().toString(),
                entity.getEstimatedValue(),
                entity.getAcquisitionDate()
        );
    }

    public static Asset toEntity(AssetDTO dto) {
        Asset entity = new Asset();
        entity.setAssetType(dto.assetType());
        entity.setEstimatedValue(dto.estimatedValue());
        entity.setAcquisitionDate(dto.acquisitionDate());
        return entity;
    }
}

