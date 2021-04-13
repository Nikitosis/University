package com.oop.entities.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
