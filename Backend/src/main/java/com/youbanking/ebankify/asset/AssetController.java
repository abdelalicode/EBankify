package com.youbanking.ebankify.asset;

import com.youbanking.ebankify.asset.Asset;
import com.youbanking.ebankify.asset.AssetRespository;
import com.youbanking.ebankify.common.BaseController;
import com.youbanking.ebankify.exception.NotFoundException;
import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/assets")
public class AssetController extends BaseController {

    private final AssetRespository assetRepo;
    private final UserRepository userRepository;

    public AssetController(AssetRespository assetRepo, UserRepository userRepository) {
        this.assetRepo = assetRepo;
        this.userRepository = userRepository;
    }


    @GetMapping
    List<Asset> getAllAssets() {
        return assetRepo.findAll();
    }

    @GetMapping("/{id}")
    Asset findById(@PathVariable Long id) {
        Optional<Asset> asset = assetRepo.findById(id);
        if (asset.isEmpty()) {
            throw new NotFoundException("Asset not found");
        }
        return asset.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    AssetResponseDTO createAsset(@Valid @RequestBody AssetDTO assetDTO, HttpServletRequest request) {
        Long userId = getUserId(request);
        Asset asset = AssetMapper.toEntity(assetDTO);
        User client = userRepository.findById(userId).orElse(null);
        asset.setOwner(client);
        Asset assetCreated = assetRepo.save(asset);

        return AssetMapper.toResponseDTO(assetCreated);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    Asset updateAsset(@RequestBody Asset asset, @PathVariable Long id) {
        return assetRepo.save(asset);
    }


}
