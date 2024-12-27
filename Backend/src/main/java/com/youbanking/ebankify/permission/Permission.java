package com.youbanking.ebankify.permission;

import com.youbanking.ebankify.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PermissionType name;


    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles = new ArrayList<>();

}
