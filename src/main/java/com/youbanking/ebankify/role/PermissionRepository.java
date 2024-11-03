package com.youbanking.ebankify.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByName(PermissionType name);

    Permission findByName(PermissionType permissionType);
}
