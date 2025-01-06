package com.youbanking.ebankify.asset;

import com.youbanking.ebankify.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRespository extends JpaRepository<Asset, Long> {
    List<Asset> findByOwnerId(Long userId);
}
