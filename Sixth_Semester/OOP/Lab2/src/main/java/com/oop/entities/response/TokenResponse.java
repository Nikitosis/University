package com.oop.entities.response;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;
    private String refreshToken;
}
