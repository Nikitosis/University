package com.oop.entities.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String password;
}
