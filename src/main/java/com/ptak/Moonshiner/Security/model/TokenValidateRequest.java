package com.ptak.Moonshiner.Security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenValidateRequest {

    private String username;
    private String token;

    public TokenValidateRequest(){}
}
