package com.youbanking.ebankify.permission;

import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public boolean hasPermission(Long userId, String permission) {


        return true;
    }
}
