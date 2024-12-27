package com.youbanking.ebankify.auth;

import com.youbanking.ebankify.role.Role;
import com.youbanking.ebankify.role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
