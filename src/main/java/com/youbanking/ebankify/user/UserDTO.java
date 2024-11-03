package com.youbanking.ebankify.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String phone;

}