package com.youbanking.ebankify.permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByName(PermissionType name);

    Permission findByName(PermissionType permissionType);
}
