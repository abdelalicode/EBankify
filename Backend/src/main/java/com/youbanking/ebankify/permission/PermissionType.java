package com.youbanking.ebankify.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum PermissionType {
        MANAGE_USERS("manage:users"),
        FULL_ACCESS("full:access"),
        APPROVE_TRANSACTIONS("approve:transactions"),
        VIEW_ALL_ACCOUNTS("view:accounts"),
        MANAGE_OWN_ACCOUNT("manage:own_account"),
        VIEW_OWN_ACCOUNT("view:own_account");


        private final String permission;
}




