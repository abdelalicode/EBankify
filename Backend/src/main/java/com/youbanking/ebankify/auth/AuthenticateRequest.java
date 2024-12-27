package com.youbanking.ebankify.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequest {

    private String email;
    private String password;

}
