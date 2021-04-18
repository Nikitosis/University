package com.oop.entities.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenRequest {
    @NotNull
    private String refreshToken;
}
