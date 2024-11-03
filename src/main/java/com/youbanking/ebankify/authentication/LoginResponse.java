package com.youbanking.ebankify.authentication;

import com.youbanking.ebankify.user.UserReturnDTO;
import lombok.*;

@Setter
@Getter
@Builder

public class LoginResponse {

    private String token;
    private UserReturnDTO user;

    public LoginResponse(String token, UserReturnDTO user) {
        this.token = token;
        this.user = user;
    }
}
