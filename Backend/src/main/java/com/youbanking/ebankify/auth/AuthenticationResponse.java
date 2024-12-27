package com.youbanking.ebankify.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {


    @JsonProperty("token")
    private String token;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
